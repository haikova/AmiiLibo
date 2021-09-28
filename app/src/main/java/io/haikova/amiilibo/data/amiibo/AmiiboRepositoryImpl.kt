package io.haikova.amiilibo.data.amiibo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.haikova.amiilibo.data.*
import io.haikova.amiilibo.data.api.AmiiboApi
import io.haikova.amiilibo.data.api.AmiiboDto
import io.haikova.amiilibo.data.api.AmiiboResponseDto
import io.haikova.amiilibo.data.db.*
import io.haikova.amiilibo.presentation.main.AmiiboOptionsData
import io.haikova.amiilibo.presentation.main.AmiiboPreferences
import java.util.*
import javax.inject.Inject

class AmiiboRepositoryImpl @Inject constructor(
  private val amiiboDao: AmiiboDao,
  private val amiiboPreferences: AmiiboPreferences,
  private val api: AmiiboApi
) : AmiiboRepository {

  override suspend fun getAllAmiibo(): List<AmiiboModel> {
    return if (isDataUpToDate()) {
      amiiboDao.getAllAmiibo().map { it.model() }
    } else {
      amiiboDao.insertAllAmiibo(api.getAllAmiibo().model().map { it.entity() })
      amiiboDao.getAllAmiibo().map { it.model() }
    }
  }

  override suspend fun getAmiiboByOptions(
    amiiboOptionsData: AmiiboOptionsData
  ): List<AmiiboModel> {
    return amiiboDao.getAmiiboByOptions(
      if (AmiiboListType.OWNED.name.equals(amiiboOptionsData.listType, true)) true else null,
      if (AmiiboListType.WHISHLIST.name.equals(amiiboOptionsData.listType, true)) true else null,
      amiiboOptionsData.amiiboSeries,
      amiiboOptionsData.gameSeries,
      amiiboOptionsData.amiiboType,
      amiiboOptionsData.character
    ).map { it.model() }
  }

  override suspend fun isDataUpToDate(): Boolean {
    return api.getDataLastUpdate().lastUpdated == amiiboPreferences.lastDataUpdate
  }

  override fun getAmiiboDetails(id: String): LiveData<AmiiboModel> {
    return amiiboDao.getAmiiboDetails(id).map { entity -> entity.model() }
  }

  override fun getAllAmiiboFromDB(): List<AmiiboModel> {
    return amiiboDao.getAllAmiibo().map { it.model() }
  }

  override suspend fun updateAmiiboDb() {
    amiiboDao.insertAllAmiibo(api.getAllAmiibo().model().map { it.entity() })
  }

  override suspend fun updateFavouriteStateAmiibo(id: String, state: Boolean) {
    amiiboDao.updateFavouriteStateAmiibo(FavouriteUpdate(id, state))
  }

  override suspend fun updateOwnedStateAmiibo(id: String, state: Boolean) {
    amiiboDao.updateOwnedStateAmiibo(OwnedUpdate(id, state))
  }

  private fun AmiiboResponseDto.model(): List<AmiiboModel> {
    return amiibo.map { it.model() }
  }

  private fun AmiiboDto.model(): AmiiboModel {
    return AmiiboModel(
      id = this.head + this.tail,
      amiiboSeries = this.amiiboSeries,
      character = this.character,
      gameSeries = this.gameSeries,
      head = this.head,
      image = this.image,
      name = this.name,
      releaseCountryMap = this.release?.let {
        mapOf(
          "au" to this.release.au,
          "eu" to this.release.eu,
          "jp" to this.release.jp,
          "na" to this.release.na
        )
      } ?: emptyMap(),
      tail = this.tail,
      type = AmiiboType.valueOf(this.type.toUpperCase(Locale.getDefault())),
    )
  }

  private fun AmiiboEntity.model(): AmiiboModel {
    return AmiiboModel(
      id = id,
      amiiboSeries = this.amiiboSeries,
      character = this.character,
      gameSeries = this.gameSeries,
      head = this.head,
      image = this.image,
      name = this.name,
      releaseCountryMap = mapOf(
        "au" to this.auRelease,
        "eu" to this.euRelease,
        "jp" to this.jpRelease,
        "na" to this.naRelease
      ),
      tail = this.tail,
      type = AmiiboType.valueOf(this.type.toUpperCase(Locale.getDefault())),
      isOwned = isOwned,
      isFavourite = isFavourite
    )
  }

  private fun AmiiboModel.entity(): AmiiboEntity {
    return AmiiboEntity(
      id = id,
      amiiboSeries = this.amiiboSeries,
      character = this.character,
      gameSeries = this.gameSeries,
      head = this.head,
      image = this.image,
      name = this.name,
      auRelease = releaseCountryMap["au"] ?: "NaN",
      euRelease = releaseCountryMap["eu"] ?: "NaN",
      jpRelease = releaseCountryMap["jp"] ?: "NaN",
      naRelease = releaseCountryMap["na"] ?: "NaN",
      tail = this.tail,
      type = this.type.toString(),
      isOwned = isOwned,
      isFavourite = isFavourite
    )
  }
}
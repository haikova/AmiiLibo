package io.haikova.amiilibo.data.amiibo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.AmiiboType
import io.haikova.amiilibo.data.api.AmiiboApi
import io.haikova.amiilibo.data.api.AmiiboDto
import io.haikova.amiilibo.data.api.AmiiboResponseDto
import io.haikova.amiilibo.data.db.AmiiboDao
import io.haikova.amiilibo.data.db.AmiiboEntity
import io.haikova.amiilibo.data.db.FavouriteUpdate
import io.haikova.amiilibo.data.db.OwnedUpdate
import io.haikova.amiilibo.presentation.home.AmiiboOptionsData
import io.haikova.amiilibo.presentation.home.AmiiboPreferences
import javax.inject.Inject

class AmiiboRepositoryImpl @Inject constructor(
  private val amiiboDao: AmiiboDao,
  private val amiiboPreferences: AmiiboPreferences,
  private val api: AmiiboApi
) : AmiiboRepository {

  override fun getAllAmiibo(): LiveData<List<AmiiboModel>> {
    return amiiboDao.getAllAmiiboLiveData().map { it.map { entity -> entity.model() } }
  }

  override fun getAmiiboByOptions(
    amiiboOptionsData: AmiiboOptionsData,
    searchData: String
  ): LiveData<List<AmiiboModel>> {
    return if (amiiboOptionsData.amiiboSeries.isEmpty() &&
      amiiboOptionsData.gameSeries.isEmpty() &&
      amiiboOptionsData.gameSeries.isEmpty() &&
      amiiboOptionsData.character.isEmpty())
      amiiboDao.getAllAmiiboLiveData(searchData).map { it.map { entity -> entity.model() } }
    else
      amiiboDao.getAmiiboByOptions(
        amiiboOptionsData.amiiboSeries,
        amiiboOptionsData.gameSeries,
        amiiboOptionsData.amiiboType,
        amiiboOptionsData.character,
        searchData
      ).map { data ->
        data.map { it.model() } }
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
      type = AmiiboType.valueOf(this.type.uppercase()),
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

fun AmiiboEntity.model(): AmiiboModel {
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
    type = AmiiboType.valueOf(this.type.uppercase()),
    isOwned = isOwned,
    isFavourite = isFavourite
  )
}
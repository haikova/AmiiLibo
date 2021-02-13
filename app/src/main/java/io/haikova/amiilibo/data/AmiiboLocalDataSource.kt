package io.haikova.amiilibo.data

import io.haikova.amiilibo.data.db.AmiiboDao
import io.haikova.amiilibo.data.db.AmiiboEntity
import io.haikova.amiilibo.presentation.main.AmiiboPreferences
import java.util.*
import javax.inject.Inject

class AmiiboLocalDataSource @Inject constructor(
  private val amiiboDao: AmiiboDao,
  private val amiiboPreferences: AmiiboPreferences
) : AmiiboDataSource {

  override suspend fun getAllAmiibo(): List<AmiiboModel> {
    return amiiboDao.getAllAmiibo().map { it.model() }
  }

  override fun saveAllAmiibo(amiiboList: List<AmiiboModel>) {
    amiiboDao.insertAllAmiibo(amiiboList.map { it.entity() })
  }

  override fun getAmiiboByOptions(
    amiiboSeries: String?,
    gameSeries: String?,
    amiiboType: String?,
    character: String?
  ): List<AmiiboModel> {
    return amiiboDao.getAmiiboByOptions(
      amiiboSeries = amiiboSeries,
      gameSeries = gameSeries,
      amiiboType = amiiboType,
      character =character
      ).map { it.model() }
  }

  override suspend fun getDataLastUpdate(): String? {
    return amiiboPreferences.lastDataUpdate
  }


  override suspend fun getAmiiboDetails(id: String): AmiiboModel {
    return amiiboDao.getAmiiboDetails(id).model()
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
      type = AmiiboType.valueOf(this.type.toUpperCase(Locale.getDefault()))
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
      type = this.type.toString()
    )
  }
}
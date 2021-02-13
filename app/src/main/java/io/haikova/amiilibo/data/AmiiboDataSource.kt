package io.haikova.amiilibo.data

interface AmiiboDataSource {
  suspend fun getAllAmiibo(): List<AmiiboModel>
  fun saveAllAmiibo(amiiboList: List<AmiiboModel>)
  fun getAmiiboByOptions(
    amiiboSeries: String?,
    gameSeries: String?,
    amiiboType: String?,
    character: String?
  ): List<AmiiboModel>

  suspend fun getDataLastUpdate(): String?
}
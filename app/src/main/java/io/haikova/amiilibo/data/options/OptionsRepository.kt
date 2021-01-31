package io.haikova.amiilibo.data.options

interface OptionsRepository {
  fun getListOptions(): List<String>
  suspend fun getAmiiboSeriesOptions(): List<String>
  suspend fun getGameSeriesOptions(): List<String>
  suspend fun getAmiiboTypeOptions(): List<String>
  suspend fun getCharacterOptions(): List<String>
}
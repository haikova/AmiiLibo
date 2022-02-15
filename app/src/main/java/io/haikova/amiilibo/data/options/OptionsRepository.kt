package io.haikova.amiilibo.data.options

import io.haikova.amiilibo.data.OptionModel

interface OptionsRepository {
  fun getListOptions(): List<String>
  suspend fun getAmiiboSeriesOptions(): List<String>
  suspend fun getGameSeriesOptions(): List<String>
  suspend fun getAmiiboTypeOptions(): List<String>
  suspend fun getCharacterOptions(): List<String>
  suspend fun updateOptionsDb()
  suspend fun getOptions(): List<OptionModel>
}
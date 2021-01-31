package io.haikova.amiilibo.data.options

interface OptionsDataSource {
  suspend fun getAllAmiiboCharacters(): List<String>
  suspend fun getAllAmiiboSeries(): List<String>
  suspend fun getAllAmiiboGameSeries(): List<String>
  suspend fun getAllAmiiboType(): List<String>
}
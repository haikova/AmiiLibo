package io.haikova.amiilibo.data.options

import io.haikova.amiilibo.data.AmiiboApi
import io.haikova.amiilibo.data.AmiiboOptionsResponseDto
import javax.inject.Inject

class OptionsRemoteDataSource @Inject constructor(
  private val api: AmiiboApi
) : OptionsDataSource {

  override suspend fun getAllAmiiboType(): List<String> {
    return api.getAllAmiiboType().model()
  }

  override suspend fun getAllAmiiboGameSeries(): List<String> {
    return api.getAllAmiiboGameSeries().model()
  }

  override suspend fun getAllAmiiboSeries(): List<String> {
    return api.getAllAmiiboSeries().model()
  }

  override suspend fun getAllAmiiboCharacters(): List<String> {
    return api.getAllAmiiboCharacters().model()
  }

  private fun AmiiboOptionsResponseDto.model(): List<String> {
    return amiiboOptions.map { it.name }
  }
}
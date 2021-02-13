package io.haikova.amiilibo.data.options

import io.haikova.amiilibo.presentation.options.AmiiboOptionsType
import javax.inject.Inject

class OptionsRepositoryImpl @Inject constructor(
  val optionType: AmiiboOptionsType,
  private val remoteDataSource: OptionsDataSource
): OptionsRepository {

  override fun getListOptions(): List<String> {
    return listOf("All", "My", "Whishlist")
  }

  override suspend fun getAmiiboSeriesOptions(): List<String> {
    return remoteDataSource.getAllAmiiboSeries().distinct()
  }

  override suspend fun getGameSeriesOptions(): List<String> {
    return remoteDataSource.getAllAmiiboGameSeries().distinct()
  }

  override suspend fun getAmiiboTypeOptions(): List<String> {
    return remoteDataSource.getAllAmiiboType().distinct()
  }

  override suspend fun getCharacterOptions(): List<String> {
    return remoteDataSource.getAllAmiiboCharacters().distinct()
  }
}
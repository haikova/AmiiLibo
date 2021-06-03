package io.haikova.amiilibo.data.options

import io.haikova.amiilibo.data.api.AmiiboApi
import io.haikova.amiilibo.data.api.AmiiboOptionsResponseDto
import io.haikova.amiilibo.data.db.AmiiboDao
import io.haikova.amiilibo.data.db.AmiiboOptionsEntityType
import io.haikova.amiilibo.data.db.OptionEntity
import javax.inject.Inject

class OptionsRepositoryImpl @Inject constructor(
  private val api: AmiiboApi,
  private val amiiboDao: AmiiboDao
) : OptionsRepository {

  override fun getListOptions(): List<String> {
    return listOf("All", "My", "Whishlist")
  }

  override suspend fun getAmiiboSeriesOptions(): List<String> {
    return api.getAllAmiiboSeries().model().distinct()
  }

  override suspend fun getGameSeriesOptions(): List<String> {
    return api.getAllAmiiboGameSeries().model().distinct()
  }

  override suspend fun getAmiiboTypeOptions(): List<String> {
    return api.getAllAmiiboType().model().distinct()
  }

  override suspend fun getCharacterOptions(): List<String> {
    return api.getAllAmiiboCharacters().model().distinct()
  }

  override suspend fun updateOptionsDb() {
    val series = getAmiiboSeriesOptions()
    val games = getGameSeriesOptions()
    val types = getAmiiboTypeOptions()
    val characters = getCharacterOptions()
    amiiboDao.clearOptions()
    amiiboDao.insertOptions(
      series.map { OptionEntity(it, AmiiboOptionsEntityType.GAME_SERIES.toString()) } +
          games.map { OptionEntity(it, AmiiboOptionsEntityType.AMIIBO_SERIES.toString()) } +
          types.map { OptionEntity(it, AmiiboOptionsEntityType.AMIIBO_TYPE.toString()) } +
          characters.map { OptionEntity(it, AmiiboOptionsEntityType.CHARACTER.toString()) })
  }

  private fun AmiiboOptionsResponseDto.model(): List<String> {
    return amiiboOptions.map { it.name }
  }

/*  override suspend fun getAllAmiiboCharacters(): List<String> {
    return amiiboDao.getOptionsByType(AmiiboOptionsEntityType.CHARACTER.toString()).map { it.name }
  }

  override suspend fun getAllAmiiboSeries(): List<String> {
    return amiiboDao.getOptionsByType(AmiiboOptionsEntityType.AMIIBO_SERIES.toString()).map { it.name }
  }

  override suspend fun getAllAmiiboGameSeries(): List<String> {
    return amiiboDao.getOptionsByType(AmiiboOptionsEntityType.GAME_SERIES.toString()).map { it.name }
  }

  override suspend fun getAllAmiiboType(): List<String> {
    return amiiboDao.getOptionsByType(AmiiboOptionsEntityType.AMIIBO_TYPE.toString()).map { it.name }
  }*/
}
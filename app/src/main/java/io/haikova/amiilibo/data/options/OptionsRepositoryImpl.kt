package io.haikova.amiilibo.data.options

import io.haikova.amiilibo.data.AmiiboOptionsType
import io.haikova.amiilibo.data.OptionModel
import io.haikova.amiilibo.data.api.AmiiboApi
import io.haikova.amiilibo.data.api.AmiiboOptionsResponseDto
import io.haikova.amiilibo.data.db.AmiiboDao
import io.haikova.amiilibo.data.db.AmiiboListType
import io.haikova.amiilibo.data.db.AmiiboOptionsEntityType
import io.haikova.amiilibo.data.db.OptionEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class OptionsRepositoryImpl @Inject constructor(
  private val api: AmiiboApi,
  private val amiiboDao: AmiiboDao
) : OptionsRepository {

  override fun getListOptions(): List<String> {
    return listOf(
      AmiiboListType.ALL.title,
      AmiiboListType.OWNED.title,
      AmiiboListType.WHISHLIST.title
    )
  }

  override suspend fun getAmiiboSeriesOptions(): List<String> {
    return amiiboDao.getOptionsByType(AmiiboOptionsEntityType.AMIIBO_SERIES.toString())
      .map { it.name }
  }

  override suspend fun getGameSeriesOptions(): List<String> {
    return amiiboDao.getOptionsByType(AmiiboOptionsEntityType.GAME_SERIES.toString())
      .map { it.name }
  }

  override suspend fun getAmiiboTypeOptions(): List<String> {
    return amiiboDao.getOptionsByType(AmiiboOptionsEntityType.AMIIBO_TYPE.toString())
      .map { it.name }
  }

  override suspend fun getCharacterOptions(): List<String> {
    return amiiboDao.getOptionsByType(AmiiboOptionsEntityType.CHARACTER.toString()).map { it.name }
  }

  override suspend fun getOptions(): List<OptionModel> {
    return amiiboDao.getAllOptions().map { OptionModel(it.name, AmiiboOptionsType.valueOf(it.type)) }
  }

  override suspend fun updateOptionsDb() {
    coroutineScope {
      val series = async { api.getAllAmiiboSeries().model().distinct() }
      val games = async { api.getAllAmiiboGameSeries().model().distinct() }
      val types = async { api.getAllAmiiboType().model().distinct() }
      val characters = async { api.getAllAmiiboCharacters().model().distinct() }
      amiiboDao.clearOptions()
      amiiboDao.insertOptions(
        series.await().map { OptionEntity(it, AmiiboOptionsEntityType.AMIIBO_SERIES.toString()) } +
            games.await().map { OptionEntity(it, AmiiboOptionsEntityType.GAME_SERIES.toString()) } +
            types.await().map { OptionEntity(it, AmiiboOptionsEntityType.AMIIBO_TYPE.toString()) } +
            characters.await()
              .map { OptionEntity(it, AmiiboOptionsEntityType.CHARACTER.toString()) })
    }
  }

  private fun AmiiboOptionsResponseDto.model(): List<String> {
    return amiiboOptions.map { it.name }
  }
}
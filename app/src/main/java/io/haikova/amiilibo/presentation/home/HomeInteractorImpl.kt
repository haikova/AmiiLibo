package io.haikova.amiilibo.presentation.home

import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.amiibo.AmiiboRepository
import io.haikova.amiilibo.data.options.OptionsRepository
import io.haikova.amiilibo.domain.HomeInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

class HomeInteractorImpl @Inject constructor(
  private val amiiboRepository: AmiiboRepository,
  private val optionsRepository: OptionsRepository
) : HomeInteractor {

  private val amiiboData = Channel<List<AmiiboModel>>(Channel.CONFLATED)
  private val progressData = Channel<Boolean>(Channel.CONFLATED)

  override suspend fun getAmiiboData(): Flow<List<AmiiboModel>> = amiiboData.consumeAsFlow()
  override suspend fun getProgressData(): Flow<Boolean> = progressData.consumeAsFlow()

  override suspend fun initAmiibo() {
    progressData.send(true)
    amiiboData.send(amiiboRepository.getAllAmiiboFromDB())
    if (!amiiboRepository.isDataUpToDate()) {
      amiiboRepository.updateAmiiboDb()
      amiiboData.send(amiiboRepository.getAllAmiiboFromDB())
      optionsRepository.updateOptionsDb()
    }
    progressData.send(false)
  }
}
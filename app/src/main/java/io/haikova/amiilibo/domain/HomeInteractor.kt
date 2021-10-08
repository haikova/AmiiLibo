package io.haikova.amiilibo.domain

import io.haikova.amiilibo.data.AmiiboModel
import kotlinx.coroutines.flow.Flow

interface HomeInteractor {
  suspend fun getAmiiboData(): Flow<List<AmiiboModel>>
  suspend fun initAmiibo()
  suspend fun getProgressData(): Flow<Boolean>
}
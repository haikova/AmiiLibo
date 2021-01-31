package io.haikova.amiilibo.data

import io.haikova.amiilibo.presentation.main.LocalDataSource
import io.haikova.amiilibo.presentation.main.RemoteDataSource
import javax.inject.Inject

class AmiiboRepositoryImpl @Inject constructor(
  @RemoteDataSource val remoteDataSource: AmiiboDataSource,
  @LocalDataSource val localDataSource: AmiiboDataSource
) : AmiiboRepository {
  override suspend fun getAllAmiibo(): List<AmiiboModel> {
    return remoteDataSource.getAllAmiibo()
  }
}
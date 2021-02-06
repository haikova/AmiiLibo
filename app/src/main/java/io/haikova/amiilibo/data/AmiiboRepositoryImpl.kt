package io.haikova.amiilibo.data

import io.haikova.amiilibo.presentation.main.AmiiboOptionsData
import io.haikova.amiilibo.presentation.main.LocalDataSource
import io.haikova.amiilibo.presentation.main.RemoteDataSource
import javax.inject.Inject

class AmiiboRepositoryImpl @Inject constructor(
  @RemoteDataSource val remoteDataSource: AmiiboDataSource,
  @LocalDataSource val localDataSource: AmiiboDataSource
) : AmiiboRepository {
  override suspend fun getAllAmiibo(): List<AmiiboModel> {
    localDataSource.saveAllAmiibo(remoteDataSource.getAllAmiibo())
    return localDataSource.getAllAmiibo()
  }

  override suspend fun getAmiiboByOptions(
    amiiboOptionsData: AmiiboOptionsData
  ): List<AmiiboModel> {
    return localDataSource.getAmiiboByOptions(
      amiiboOptionsData.amiiboSeries,
      amiiboOptionsData.gameSeries,
      amiiboOptionsData.amiiboType,
      amiiboOptionsData.character
    )
  }
}
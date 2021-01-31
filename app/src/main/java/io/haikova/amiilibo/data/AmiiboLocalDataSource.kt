package io.haikova.amiilibo.data

import javax.inject.Inject

class AmiiboLocalDataSource @Inject constructor(

) : AmiiboDataSource{
  override suspend fun getAllAmiibo(): List<AmiiboModel> {
    return emptyList()
  }
}
package io.haikova.amiilibo.data.amiibo

import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.presentation.main.AmiiboOptionsData

interface AmiiboRepository {
  suspend fun getAllAmiibo(): List<AmiiboModel>
  suspend fun getAmiiboByOptions(amiiboOptionsData: AmiiboOptionsData): List<AmiiboModel>
  suspend fun getAmiiboDetails(id: String): AmiiboModel
  suspend fun isDataUpToDate(): Boolean
  suspend fun getAllAmiiboFromDB(): List<AmiiboModel>
  suspend fun updateAmiiboDb()
  suspend fun updateFavouriteStateAmiibo(id: String, state: Boolean)
  suspend fun updateOwnedStateAmiibo(id: String, state: Boolean)
}

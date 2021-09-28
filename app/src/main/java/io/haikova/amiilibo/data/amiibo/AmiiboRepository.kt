package io.haikova.amiilibo.data.amiibo

import androidx.lifecycle.LiveData
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.presentation.main.AmiiboOptionsData

interface AmiiboRepository {
  suspend fun getAllAmiibo(): List<AmiiboModel>
  suspend fun getAmiiboByOptions(amiiboOptionsData: AmiiboOptionsData): List<AmiiboModel>
  fun getAmiiboDetails(id: String): LiveData<AmiiboModel>
  suspend fun isDataUpToDate(): Boolean
  fun getAllAmiiboFromDB(): List<AmiiboModel>
  suspend fun updateAmiiboDb()
  suspend fun updateFavouriteStateAmiibo(id: String, state: Boolean)
  suspend fun updateOwnedStateAmiibo(id: String, state: Boolean)
}

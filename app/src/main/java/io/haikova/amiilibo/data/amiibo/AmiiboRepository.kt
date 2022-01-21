package io.haikova.amiilibo.data.amiibo

import androidx.lifecycle.LiveData
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.presentation.home.AmiiboOptionsData

interface AmiiboRepository {
  fun getAllAmiibo(): LiveData<List<AmiiboModel>>
  fun getAmiiboByOptions(amiiboOptionsData: AmiiboOptionsData, searchData: String): LiveData<List<AmiiboModel>>
  fun getAmiiboDetails(id: String): LiveData<AmiiboModel>
  suspend fun isDataUpToDate(): Boolean
  fun getAllAmiiboFromDB(): List<AmiiboModel>
  suspend fun updateAmiiboDb()
  suspend fun updateFavouriteStateAmiibo(id: String, state: Boolean)
  suspend fun updateOwnedStateAmiibo(id: String, state: Boolean)
}

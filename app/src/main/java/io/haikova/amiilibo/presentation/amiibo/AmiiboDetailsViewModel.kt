package io.haikova.amiilibo.presentation.amiibo

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.amiibo.AmiiboRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AmiiboDetailsViewModel @Inject constructor(
  private val amiiboRepository: AmiiboRepositoryImpl,
  amiiboId: String
) : ViewModel() {

  private val _amiiboData: LiveData<AmiiboModel> = amiiboRepository.getAmiiboDetails(amiiboId)
  val amiiboData: LiveData<AmiiboModel> = _amiiboData

  fun changeOwnedState() {
    amiiboData.value?.let {
      viewModelScope.launch(Dispatchers.IO) {
        amiiboRepository.updateOwnedStateAmiibo(it.id, !it.isOwned)
      }
    }
  }

  fun changeFavouriteState() {
    amiiboData.value?.let {
      viewModelScope.launch(Dispatchers.IO) {
        amiiboRepository.updateFavouriteStateAmiibo(it.id, !it.isFavourite)
      }
    }
  }

}
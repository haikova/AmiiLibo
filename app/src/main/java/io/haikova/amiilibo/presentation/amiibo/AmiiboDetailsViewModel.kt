package io.haikova.amiilibo.presentation.amiibo

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.AmiiboRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AmiiboDetailsViewModel @Inject constructor(
  private val amiiboRepository: AmiiboRepositoryImpl
) : ViewModel() {

  private val _amiiboData: MutableLiveData<AmiiboModel> by lazy {
    MutableLiveData<AmiiboModel>()
  }
  val amiiboData: LiveData<AmiiboModel> = _amiiboData

  fun loadData(itemId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _amiiboData.postValue(amiiboRepository.getAmiiboDetails(itemId))
    }
  }

}
package io.haikova.amiilibo.presentation.options

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.options.OptionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(
  private val optionsRepository: OptionsRepository,
  private val optionType: AmiiboOptionsType
): ViewModel() {

  fun selectOption(text: String) {
    _amiiboResult.postValue(optionType to text)
  }

  private val _amiiboOptionsList = MutableLiveData<List<String>>()
  val amiiboOptionsList : LiveData<List<String>> = _amiiboOptionsList

  private val _amiiboResult = MutableLiveData<Pair<AmiiboOptionsType, String>>()
  val amiiboResult : LiveData<Pair<AmiiboOptionsType, String>> = _amiiboResult

  init {
    viewModelScope.launch(Dispatchers.IO) {
      _amiiboOptionsList.postValue(
        when (optionType) {
          AmiiboOptionsType.AMIIBO_SERIES -> optionsRepository.getAmiiboSeriesOptions()
          AmiiboOptionsType.GAME_SERIES -> optionsRepository.getGameSeriesOptions()
          AmiiboOptionsType.AMIIBO_TYPE -> optionsRepository.getAmiiboTypeOptions()
          AmiiboOptionsType.CHARACTER -> optionsRepository.getCharacterOptions()
        }
      )
    }
  }
}

enum class AmiiboOptionsType {
  AMIIBO_SERIES, GAME_SERIES, AMIIBO_TYPE, CHARACTER
}

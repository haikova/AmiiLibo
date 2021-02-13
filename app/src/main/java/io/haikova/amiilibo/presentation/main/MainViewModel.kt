package io.haikova.amiilibo.presentation.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.AmiiboRepository
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.options.AmiiboOptionsType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val amiiboRepository: AmiiboRepository
) : ViewModel() {

  private val _amiiboData: MutableLiveData<List<ListItem>> by lazy {
    MutableLiveData<List<ListItem>>()
  }
  val amiiboData: LiveData<List<ListItem>> = _amiiboData

  private val _isProgressShow: MutableLiveData<Boolean> by lazy {
    MutableLiveData<Boolean>(false)
  }
  val isProgressShow: LiveData<Boolean> = _isProgressShow

  private val _amiiboOptions: MutableLiveData<AmiiboOptionsData> by lazy {
    MutableLiveData<AmiiboOptionsData>()
  }
  val amiiboOptions: LiveData<AmiiboOptionsData> = _amiiboOptions


  init {
    loadAmiiboData()
  }

  private fun loadAmiiboData() {
    viewModelScope.launch(Dispatchers.IO) {
      _isProgressShow.postValue(true)
      _amiiboData.postValue(
        amiiboRepository.getAllAmiibo().map { it.map() }
      )
      _isProgressShow.postValue(false)
    }
  }

  fun getAmiiboByOptions(amiiboOptions: AmiiboOptionsData) {
    viewModelScope.launch(Dispatchers.IO) {
      _isProgressShow.postValue(true)
      _amiiboData.postValue(
        amiiboRepository.getAmiiboByOptions(amiiboOptions).map { it.map() }
      )
      _isProgressShow.postValue(false)
    }
  }

  fun updateAmiiboOptions(type: AmiiboOptionsType, value: String?) {
    when (type) {
      AmiiboOptionsType.LIST -> {
      }
      AmiiboOptionsType.AMIIBO_SERIES -> {
        _amiiboOptions.postValue(
          _amiiboOptions.value?.copy(amiiboSeries = value)
            ?: AmiiboOptionsData(amiiboSeries = value)
        )
      }
      AmiiboOptionsType.GAME_SERIES -> {
        _amiiboOptions.postValue(
          _amiiboOptions.value?.copy(gameSeries = value)
            ?: AmiiboOptionsData(gameSeries = value)
        )
      }
      AmiiboOptionsType.AMIIBO_TYPE -> {
        _amiiboOptions.postValue(
          _amiiboOptions.value?.copy(amiiboType = value)
            ?: AmiiboOptionsData(amiiboType = value)
        )
      }
      AmiiboOptionsType.CHARACTER -> {
        _amiiboOptions.postValue(
          _amiiboOptions.value?.copy(character = value)
            ?: AmiiboOptionsData(character = value)
        )
      }
    }

  }

  private fun AmiiboModel.map(): AmiiboItem {
    return AmiiboItem(
      id = this.id,
      head = this.head,
      tail = this.tail,
      image = this.image
    )
  }

  private fun isDataUpToDate() {

  }

}
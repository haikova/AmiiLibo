package io.haikova.amiilibo.presentation.home


import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.amiibo.AmiiboRepository
import io.haikova.amiilibo.domain.HomeInteractor
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.home.adapter.AmiiboItem
import io.haikova.amiilibo.presentation.options.AmiiboOptionsType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val mainInteractor: HomeInteractor,
  private val amiiboRepository: AmiiboRepository
) : ViewModel() {

  private val amiiboOptionsData: MutableLiveData<AmiiboOptionsData> = MutableLiveData()

  private val _amiiboData: LiveData<List<ListItem>> = amiiboRepository.getAllAmiibo().map { it.map {model -> model.map()} }
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
    viewModelScope.launch {
      mainInteractor.getProgressData().collect {
        _isProgressShow.postValue(it)
      }
    }
    viewModelScope.launch(Dispatchers.IO) {
      mainInteractor.initAmiibo()
    }
  }


  fun getAmiiboByOptions(amiiboOptions: AmiiboOptionsData) {
/*    viewModelScope.launch(Dispatchers.IO) {
      _isProgressShow.postValue(true)
      _amiiboData.postValue(
        amiiboRepository.getAmiiboByOptions(amiiboOptions).map { it.map() }
      )
      _isProgressShow.postValue(false)
    }*/
  }

  fun updateAmiiboOptions(type: AmiiboOptionsType, value: String?) {
    when (type) {
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

  private fun isDataUpToDate() {

  }

}

fun AmiiboModel.map(): AmiiboItem {
  return AmiiboItem(
    id = this.id,
    head = this.head,
    tail = this.tail,
    image = this.image,
    name = this.name,
    game = this.gameSeries,
    isOwned = this.isOwned,
    isFavourite = this.isFavourite
  )
}
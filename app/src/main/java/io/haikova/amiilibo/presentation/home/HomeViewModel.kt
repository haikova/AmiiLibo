package io.haikova.amiilibo.presentation.home


import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.AmiiboOptionsType
import io.haikova.amiilibo.data.amiibo.AmiiboRepository
import io.haikova.amiilibo.domain.HomeInteractor
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.home.adapter.AmiiboItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val mainInteractor: HomeInteractor,
  private val amiiboRepository: AmiiboRepository
) : ViewModel() {

  private val _amiiboOptions: MutableLiveData<AmiiboOptionsData> =
    MutableLiveData<AmiiboOptionsData>(AmiiboOptionsData())

  private val _searchData: MutableLiveData<String> = MutableLiveData("")

  private val _filterData = MediatorLiveData<Pair<AmiiboOptionsData, String>>().apply {
    addSource(_amiiboOptions) {
      value = Pair(it, _searchData.value ?: "")
    }
    addSource(_searchData) {
      value = Pair(_amiiboOptions.value ?: AmiiboOptionsData(), it)
    }
  }

  private val _amiiboData: LiveData<List<ListItem>> =
    Transformations.switchMap(_filterData) { param ->
      amiiboRepository.getAmiiboByOptions(param.first, param.second).map { it.map { model -> model.map() } }
    }
  val amiiboData: LiveData<List<ListItem>> = _amiiboData

  init {
    viewModelScope.launch(Dispatchers.IO) {
      mainInteractor.initAmiibo()
    }
  }

  fun getAmiiboByOptions(amiiboOptions: AmiiboOptionsData) {
    viewModelScope.launch(Dispatchers.IO) {
      _amiiboOptions.postValue(amiiboOptions)
    }
  }

  fun setSearchData(searchData: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _searchData.postValue(searchData)
    }
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
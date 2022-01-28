package io.haikova.amiilibo.presentation.home


import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.amiibo.AmiiboRepository
import io.haikova.amiilibo.domain.HomeInteractor
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.home.adapter.AmiiboItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val mainInteractor: HomeInteractor,
  private val amiiboRepository: AmiiboRepository
) : ViewModel() {

  private val _screenState = MutableLiveData<ScreenState>(HomeState)
  val screenState = _screenState

  private val _amiiboOptions: MutableLiveData<AmiiboOptionsData> =
    MutableLiveData<AmiiboOptionsData>(AmiiboOptionsData())

  private val _searchData: MutableLiveData<String> = MutableLiveData("")
  val searchData: MutableLiveData<String> = _searchData

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

  fun changeState(focused: Boolean, searchQuery: String) {
    _screenState.value = when {
      focused && searchQuery.isBlank() -> SearchInitialState
      focused && searchQuery.isNotBlank() -> SearchingState
      !focused -> HomeState
      else -> HomeState
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

sealed class ScreenState {
  open val isFilterButtonVisible = true
  open val isSearchImageVisible = false
  open val isSearchTextVisible = false
  open val isListVisible = true
}

object HomeState : ScreenState()
object SearchInitialState : ScreenState() {
  override val isFilterButtonVisible = false
  override val isSearchImageVisible = true
  override val isSearchTextVisible = true
  override val isListVisible = false
}
object SearchingState : ScreenState() {
  override val isFilterButtonVisible = false
}
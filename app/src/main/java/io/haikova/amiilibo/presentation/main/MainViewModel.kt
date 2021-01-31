package io.haikova.amiilibo.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.AmiiboDataSource
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.AmiiboRepository
import io.haikova.amiilibo.presentation.common.ListItem
import kotlinx.coroutines.Dispatchers
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

  init {
    loadAmiiboData()
  }

  private fun loadAmiiboData() {
    viewModelScope.launch(Dispatchers.IO) {
      _amiiboData.postValue(
        amiiboRepository.getAllAmiibo().map { it.map() }
      )
    }
  }

  private fun AmiiboModel.map(): AmiiboItem {
    return AmiiboItem(
      head = this.head,
      tail = this.tail,
      image = this.image
    )
  }
}
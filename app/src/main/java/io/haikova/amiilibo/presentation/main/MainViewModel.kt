package io.haikova.amiilibo.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.AmiiboRemoteDataSource
import io.haikova.amiilibo.presentation.common.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

  val temp = AmiiboRemoteDataSource()

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
        temp.getAllAmiibo().map { it.map() }
      )
    }
  }

  private fun loadAmiiboThumbOneData() {
    _amiiboData.postValue(
      listOf(
        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        )
      )
    )
  }

  private fun loadAmiiboThumbData() {
    _amiiboData.postValue(
      listOf(
        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        ),

        AmiiboItem(
          "00000000", "00340102",
          "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00340102.png"
        )
      )
    )
  }

  private fun AmiiboModel.map(): AmiiboItem {
    return AmiiboItem(
      head = this.head,
      tail = this.tail,
      image = this.image
    )
  }
}
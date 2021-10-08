package io.haikova.amiilibo.presentation.amiibolist

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.amiibo.AmiiboRepository
import io.haikova.amiilibo.data.amiibolist.AmiiboListRepository
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.home.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AmiiboListViewModel @Inject constructor(
  amiiboListRepository: AmiiboListRepository
) : ViewModel() {

  private val _amiiboData: LiveData<List<ListItem>> =
    amiiboListRepository.getAmiiboList().map { it.map { it.map() } }
  val amiiboData: LiveData<List<ListItem>> = _amiiboData
}
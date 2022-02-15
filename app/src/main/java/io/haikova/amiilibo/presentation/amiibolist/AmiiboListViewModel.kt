package io.haikova.amiilibo.presentation.amiibolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.amiibolist.AmiiboListRepository
import io.haikova.amiilibo.data.amiibolist.EmptyCollectionData
import io.haikova.amiilibo.presentation.collection.EmptyCollectionItem
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.home.map
import javax.inject.Inject


@HiltViewModel
class AmiiboListViewModel @Inject constructor(
  private val amiiboListRepository: AmiiboListRepository
) : ViewModel() {

  private val _amiiboData: LiveData<List<ListItem>> =
    amiiboListRepository.getAmiiboList().map { it -> it.map { it.map() } }
  val amiiboData: LiveData<List<ListItem>> = _amiiboData

  fun getEmpyAmiiboData(): EmptyCollectionItem  {
    return amiiboListRepository.getEmptyData().item()
  }

  fun EmptyCollectionData.item(): EmptyCollectionItem {
    return EmptyCollectionItem(this.title, this.text)
  }
}
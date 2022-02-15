package io.haikova.amiilibo.data.amiibolist

import androidx.lifecycle.LiveData
import io.haikova.amiilibo.data.AmiiboModel

interface AmiiboListRepository {
  fun getAmiiboList(): LiveData<List<AmiiboModel>>
  fun getEmptyData(): EmptyCollectionData
}
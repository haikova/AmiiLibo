package io.haikova.amiilibo.data.amiibolist

import androidx.lifecycle.LiveData
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.db.AmiiboEntity

interface AmiiboListRepository {
  fun getAmiiboList(): LiveData<List<AmiiboModel>>
}
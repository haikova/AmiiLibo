package io.haikova.amiilibo.data.amiibolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.amiibo.model
import io.haikova.amiilibo.data.db.AmiiboDao
import io.haikova.amiilibo.data.db.AmiiboEntity
import javax.inject.Inject

class AmiiboCollectionRepository @Inject constructor(
  private val amiiboDao: AmiiboDao
): AmiiboListRepository {

  override fun getAmiiboList(): LiveData<List<AmiiboModel>> {
    return amiiboDao.getAmiiboCollectionList().map { it.map { entity -> entity.model() } }
  }
}
package io.haikova.amiilibo.data.amiibolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.amiibo.model
import io.haikova.amiilibo.data.db.AmiiboDao
import javax.inject.Inject

class AmiiboCollectionRepository @Inject constructor(
  private val amiiboDao: AmiiboDao
): AmiiboListRepository {

  override fun getAmiiboList(): LiveData<List<AmiiboModel>> {
    return amiiboDao.getAmiiboCollectionList().map { it.map { entity -> entity.model() } }
  }

  override fun getEmptyData(): EmptyCollectionData {
    return EmptyCollectionData(
      title = "Home for your Collection",
      text = "Here you will find all the items you mark as owned. Simply click “Add to collection” button on Amiibo page and you’ll see it here."
    )
  }
}
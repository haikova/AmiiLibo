package io.haikova.amiilibo.data.amiibolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.haikova.amiilibo.data.AmiiboModel
import io.haikova.amiilibo.data.amiibo.model
import io.haikova.amiilibo.data.db.AmiiboDao
import javax.inject.Inject

class AmiiboFavouriteRepository @Inject constructor(
  private val amiiboDao: AmiiboDao
): AmiiboListRepository {

  override fun getAmiiboList(): LiveData<List<AmiiboModel>> {
    return amiiboDao.getAmiiboFavouriteList().map { it.map { entity -> entity.model() } }
  }

  override fun getEmptyData(): EmptyCollectionData {
    return EmptyCollectionData(
      title = "Your Wish List",
      text = "Mark items as favourites and you’ll see them here."
    )
  }
}
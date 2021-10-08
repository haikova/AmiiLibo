package io.haikova.amiilibo.presentation.amiibolist.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.haikova.amiilibo.data.amiibolist.AmiiboCollectionRepository
import io.haikova.amiilibo.data.amiibolist.AmiiboFavouriteRepository
import io.haikova.amiilibo.data.amiibolist.AmiiboListRepository
import io.haikova.amiilibo.data.db.AmiiboDao
import io.haikova.amiilibo.presentation.amiibolist.AmiiboListFragment
import java.lang.IllegalArgumentException


@Module
@InstallIn(ViewModelComponent::class)
abstract class AmiiboListModule {

  companion object {
    @Provides
    @ViewModelScoped
    fun provideAmiiboListType(
      handle: SavedStateHandle,
      dao: AmiiboDao
    ): AmiiboListRepository {
      val type = (handle.get<String>(AmiiboListFragment.LIST_TYPE)
        ?.let {AmiiboListFragment.AmiiboListType.valueOf(it)}
        ?: throw IllegalArgumentException("Illegal amiibo list type"))
      return when (type) {
        AmiiboListFragment.AmiiboListType.COLLECTION -> AmiiboCollectionRepository(dao)
        AmiiboListFragment.AmiiboListType.FAVOURITE -> AmiiboFavouriteRepository(dao)
      }
    }
  }
}
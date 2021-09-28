package io.haikova.amiilibo.presentation.amiibo.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.haikova.amiilibo.presentation.amiibo.AmiiboDetailsFragment
import java.lang.IllegalArgumentException

@Module
@InstallIn(ViewModelComponent::class)
abstract class AmiiboDetailsModule {

  companion object {
    @Provides
    @ViewModelScoped
    fun provideAmiiboId(
      handle: SavedStateHandle
    ) : String {
      return handle.get<String>(AmiiboDetailsFragment.ITEM_ID) ?: throw IllegalArgumentException("Illegal amiibo id")
    }
  }
}
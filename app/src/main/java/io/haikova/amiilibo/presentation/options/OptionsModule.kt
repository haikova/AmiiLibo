package io.haikova.amiilibo.presentation.options

import androidx.lifecycle.SavedStateHandle
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.haikova.amiilibo.data.options.OptionsDataSource
import io.haikova.amiilibo.data.options.OptionsRemoteDataSource
import io.haikova.amiilibo.data.options.OptionsRepository
import io.haikova.amiilibo.data.options.OptionsRepositoryImpl
import io.haikova.amiilibo.presentation.options.AmiiboOptionsType.*
import io.haikova.amiilibo.presentation.options.OptionsDialogFragment.Companion.OPTIONS_TYPE
import java.lang.IllegalArgumentException

@Module
@InstallIn(ViewModelComponent::class)
abstract class OptionsModule {

  @Binds
  abstract fun bindOptionRepository(optionsRepositoryImpl: OptionsRepositoryImpl): OptionsRepository

  @Binds
  abstract fun bindOptionsDataSource(optionsRemoteDataSource: OptionsRemoteDataSource): OptionsDataSource

  companion object {
    @Provides
    @ViewModelScoped
    fun provideOptionsType(
      handle: SavedStateHandle
    ) : AmiiboOptionsType {
      return (handle.get<String>(OPTIONS_TYPE)?.let { valueOf(it) } ?: throw IllegalArgumentException("Illegal amiibo argument type"))
    }
  }
}
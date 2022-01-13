package io.haikova.amiilibo.presentation.options.di

import androidx.lifecycle.SavedStateHandle
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.haikova.amiilibo.data.options.*
import io.haikova.amiilibo.presentation.options.AmiiboOptionsType
import io.haikova.amiilibo.presentation.options.AmiiboOptionsType.*
import io.haikova.amiilibo.presentation.options.OptionsDialogFragment.Companion.OPTIONS_TYPE
import java.lang.IllegalArgumentException

@Module
@InstallIn(ViewModelComponent::class)
abstract class OptionsModule {

  @Binds
  @ViewModelScoped
  abstract fun bindOptionsRepository(optionsRepository: OptionsRepositoryImpl): OptionsRepository

}
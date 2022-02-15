package io.haikova.amiilibo.presentation.options.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.haikova.amiilibo.data.options.OptionsRepository
import io.haikova.amiilibo.data.options.OptionsRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class OptionsModule {

  @Binds
  @ViewModelScoped
  abstract fun bindOptionsRepository(optionsRepository: OptionsRepositoryImpl): OptionsRepository

}
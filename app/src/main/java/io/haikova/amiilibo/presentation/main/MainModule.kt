package io.haikova.amiilibo.presentation.main

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.haikova.amiilibo.data.amiibo.AmiiboRepository
import io.haikova.amiilibo.data.amiibo.AmiiboRepositoryImpl
import io.haikova.amiilibo.domain.MainInteractor

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainModule {

  @Binds
  @ViewModelScoped
  abstract fun bindMainInteractor(mainInteractor: MainInteractorImpl): MainInteractor

  @Binds
  @ViewModelScoped
  abstract fun bindAmiiboRepository(amiiboRepository: AmiiboRepositoryImpl): AmiiboRepository
}
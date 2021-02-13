package io.haikova.amiilibo.presentation.main

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.haikova.amiilibo.data.*
import io.haikova.amiilibo.data.db.AmiiboDao
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
abstract class OptionsModule {

  @Binds
  @ViewModelScoped
  abstract fun bindAmiiboRepository(amiiboRepository: AmiiboRepositoryImpl): AmiiboRepository

  @RemoteDataSource
  @Binds
  @ViewModelScoped
  abstract fun bindRemoteDataSource(amiiboRemoteDataSource: AmiiboRemoteDataSource): AmiiboDataSource

  @LocalDataSource
  @Binds
  @ViewModelScoped
  abstract fun bindLocaleDataSource(amiiboRemoteDataSource: AmiiboLocalDataSource): AmiiboDataSource
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource
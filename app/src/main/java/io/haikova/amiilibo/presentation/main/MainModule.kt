package io.haikova.amiilibo.presentation.main

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.haikova.amiilibo.data.*
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
abstract class OptionsModule {

  @Binds
  abstract fun bindAmiiboRepository(amiiboRepository: AmiiboRepositoryImpl): AmiiboRepository

  @RemoteDataSource
  @Binds
  abstract fun bindRemoteDataSource(amiiboRemoteDataSource: AmiiboRemoteDataSource): AmiiboDataSource

  @LocalDataSource
  @Binds
  abstract fun bindLocaleDataSource(amiiboRemoteDataSource: AmiiboLocalDataSource): AmiiboDataSource
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource
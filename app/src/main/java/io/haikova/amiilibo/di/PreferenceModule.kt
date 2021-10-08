package io.haikova.amiilibo.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.haikova.amiilibo.presentation.home.AmiiboPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
  @Provides
  @Singleton
  fun provideAmiiboPreference(application: Application): AmiiboPreferences {
    return AmiiboPreferences(application.applicationContext)
  }
}
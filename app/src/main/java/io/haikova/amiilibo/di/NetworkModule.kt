package io.haikova.amiilibo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.haikova.amiilibo.data.AmiiboApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://www.amiiboapi.com/api/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideAmiiboApi(retrofit: Retrofit): AmiiboApi {
    return retrofit.create(AmiiboApi::class.java)
  }
}
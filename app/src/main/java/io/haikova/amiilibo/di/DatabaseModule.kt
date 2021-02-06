package io.haikova.amiilibo.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.haikova.amiilibo.data.db.AmiiboDao
import io.haikova.amiilibo.data.db.AmiiboDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideDatabase(application: Application): AmiiboDatabase {
    return Room.databaseBuilder(
      application,
      AmiiboDatabase::class.java,
      "amiibo_db"
    ).build()
  }

  @Provides
  @Singleton
  fun provideAmiiboDao(amiiboDatabase: AmiiboDatabase): AmiiboDao {
    return amiiboDatabase.amiiboDao()
  }

}
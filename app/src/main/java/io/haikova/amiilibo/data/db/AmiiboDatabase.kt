package io.haikova.amiilibo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AmiiboEntity::class, OptionEntity::class], version = 1)
abstract class AmiiboDatabase : RoomDatabase() {
  abstract fun amiiboDao(): AmiiboDao
}
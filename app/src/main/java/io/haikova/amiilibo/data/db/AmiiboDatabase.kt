package io.haikova.amiilibo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(AmiiboEntity::class), version = 1)
abstract class AmiiboDatabase : RoomDatabase() {
  abstract fun amiiboDao(): AmiiboDao
}
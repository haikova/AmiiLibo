package io.haikova.amiilibo.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amiibos")
data class AmiiboEntity(
  @PrimaryKey val id: String,
  @ColumnInfo(name = "amiiboSeries") val amiiboSeries: String,
  @ColumnInfo(name = "character") val character: String,
  @ColumnInfo(name = "gameSeries") val gameSeries: String,
  @ColumnInfo(name = "head") val head: String,
  @ColumnInfo(name = "image") val image: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "au_release") val auRelease: String,
  @ColumnInfo(name = "eu_release") val euRelease: String,
  @ColumnInfo(name = "jp_release") val jpRelease: String,
  @ColumnInfo(name = "na_release") val naRelease: String,
  @ColumnInfo(name = "tail") val tail: String,
  @ColumnInfo(name = "type") val type: String,
  @ColumnInfo(name = "isOwned") val isOwned: Boolean = false,
  @ColumnInfo(name = "isFavourite") val isFavourite: Boolean = false
)

class FavouriteUpdate(
  val id: String,
  val isFavourite: Boolean
)

class OwnedUpdate(
  val id: String,
  val isOwned: Boolean
)
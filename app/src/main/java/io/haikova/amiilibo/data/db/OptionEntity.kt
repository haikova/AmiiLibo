package io.haikova.amiilibo.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "options")
data class OptionEntity (
  @PrimaryKey val name: String,
  @ColumnInfo(name = "type") val type: String
)

enum class AmiiboOptionsEntityType {
  LIST, AMIIBO_SERIES, GAME_SERIES, AMIIBO_TYPE, CHARACTER
}

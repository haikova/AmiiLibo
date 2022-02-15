package io.haikova.amiilibo.data

data class OptionModel (
  val name: String,
  val type: AmiiboOptionsType,
)

enum class AmiiboOptionsType {
  AMIIBO_SERIES, GAME_SERIES, AMIIBO_TYPE, CHARACTER
}

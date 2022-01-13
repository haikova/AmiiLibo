package io.haikova.amiilibo.data

data class OptionsData(
  val amiiboSeriesList: List<OptionModel>,
  val gameSeriesList: List<OptionModel>,
  val characterList: List<OptionModel>,
  val amiiboTypeList: List<OptionModel>
)

data class OptionModel (
  val name: String,
  val type: AmiiboOptionsType,
)

enum class AmiiboOptionsType {
  AMIIBO_SERIES, GAME_SERIES, AMIIBO_TYPE, CHARACTER
}

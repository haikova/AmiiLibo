package io.haikova.amiilibo.presentation.options.adapter

import io.haikova.amiilibo.presentation.common.ListItem

data class OptionItem(
  val name: String,
  val type: OptionsType,
  val isSelected: Boolean
): ListItem

enum class OptionsType {
  AMIIBO_SERIES, GAME_SERIES, AMIIBO_TYPE, CHARACTER
}

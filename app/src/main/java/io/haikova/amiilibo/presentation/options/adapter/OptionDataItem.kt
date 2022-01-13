package io.haikova.amiilibo.presentation.options.adapter

import io.haikova.amiilibo.presentation.common.ListItem

data class OptionsDataItem(
  val optionslist: List<OptionItem>,
  val type: OptionsType,
) : ListItem
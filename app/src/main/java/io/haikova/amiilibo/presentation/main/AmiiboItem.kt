package io.haikova.amiilibo.presentation.main

import io.haikova.amiilibo.presentation.common.ListItem

data class AmiiboItem(
  val id: String,
  val head: String,
  val tail: String,
  val image: String
) : ListItem
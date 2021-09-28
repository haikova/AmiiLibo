package io.haikova.amiilibo.presentation.main.adapter

import io.haikova.amiilibo.presentation.common.ListItem

data class AmiiboItem(
  val id: String,
  val head: String,
  val tail: String,
  val image: String,
  val name: String,
  val game: String
) : ListItem
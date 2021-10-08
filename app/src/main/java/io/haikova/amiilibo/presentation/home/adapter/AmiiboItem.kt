package io.haikova.amiilibo.presentation.home.adapter

import io.haikova.amiilibo.presentation.common.ListItem

data class AmiiboItem(
  val id: String,
  val head: String,
  val tail: String,
  val image: String,
  val name: String,
  val game: String,
  val isOwned: Boolean,
  val isFavourite: Boolean
) : ListItem
package io.haikova.amiilibo.data

data class AmiiboModel(
  val id: String,
  val amiiboSeries : String,
  val character : String,
  val gameSeries : String,
  val head : String,
  val image : String,
  val name : String,
  val releaseCountryMap: Map<String, String?>,
  val tail : String,
  val type : AmiiboType,
  val isOwned: Boolean = false,
  val isFavourite: Boolean = false
)

enum class AmiiboType {
  FIGURE, CARD, YARN, BAND
}
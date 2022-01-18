package io.haikova.amiilibo.presentation.home

data class AmiiboOptionsData(
  val amiiboSeries: List<String> = emptyList(),
  val gameSeries: List<String> = emptyList(),
  val amiiboType: List<String> = emptyList(),
  val character: List<String> = emptyList()
)
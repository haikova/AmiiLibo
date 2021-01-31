package io.haikova.amiilibo.data

import java.util.*
import javax.inject.Inject

class AmiiboRemoteDataSource @Inject constructor(
  private val api: AmiiboApi
) : AmiiboDataSource {

  override suspend fun getAllAmiibo(): List<AmiiboModel> {
    return api.getAllAmiibo().model()
  }
}

private fun AmiiboResponseDto.model(): List<AmiiboModel> {
  return amiibo.map { it.model() }
}

private fun AmiiboDto.model(): AmiiboModel {
  return AmiiboModel(
    amiiboSeries = this.amiiboSeries,
    character = this.character,
    gameSeries = this.gameSeries,
    head = this.head,
    image = this.image,
    name = this.name,
    releaseCountryMap = mapOf(
      "au" to this.release.au,
      "eu" to this.release.eu,
      "jp" to this.release.jp,
      "na" to this.release.na
    ),
    tail = this.tail,
    type = AmiiboType.valueOf(this.type.toUpperCase(Locale.getDefault()))
  )
}

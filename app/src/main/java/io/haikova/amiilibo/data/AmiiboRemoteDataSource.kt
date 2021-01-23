package io.haikova.amiilibo.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AmiiboRemoteDataSource {

  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .baseUrl("https://www.amiiboapi.com/api/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  private val api: AmiiboApi by lazy { retrofit.create(AmiiboApi::class.java) }

  suspend fun getAllAmiibo(): List<AmiiboModel> {
    return api.getAllAmiibo().model()
  }

  suspend fun getAllAmiiboType(): List<String> {
    return api.getAllAmiiboType().model()
  }

  suspend fun getAllAmiiboGameSeries(): List<String> {
    return api.getAllAmiiboGameSeries().model()
  }

  suspend fun getAllAmiiboSeries(): List<String> {
    return api.getAllAmiiboSeries().model()
  }

  suspend fun getAllAmiiboCharacters(): List<String> {
    return api.getAllAmiiboCharacters().model()
  }
}

private fun AmiiboResponseDto.model(): List<AmiiboModel> {
  return amiibo.map { it.model() }
}

private fun AmiiboOptionsResponseDto.model(): List<String> {
  return amiiboOptions.map { it.name }
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
    type = AmiiboType.valueOf(this.type)
  )
}

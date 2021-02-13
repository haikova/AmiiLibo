package io.haikova.amiilibo.data

import retrofit2.http.GET

interface AmiiboApi {

  @GET("amiibo/")
  suspend fun getAllAmiibo(): AmiiboResponseDto

  @GET("type")
  suspend fun getAllAmiiboType(): AmiiboOptionsResponseDto

  @GET("gameseries")
  suspend fun getAllAmiiboGameSeries(): AmiiboOptionsResponseDto

  @GET("amiiboseries")
  suspend fun getAllAmiiboSeries(): AmiiboOptionsResponseDto

  @GET("character")
  suspend fun getAllAmiiboCharacters(): AmiiboOptionsResponseDto

  @GET("lastupdated")
  suspend fun getDataLastUpdate(): LastUpdateDto
}
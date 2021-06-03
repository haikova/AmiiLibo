package io.haikova.amiilibo.data.api

import com.google.gson.annotations.SerializedName

data class AmiiboResponseDto(
  @SerializedName("amiibo") val amiibo: List<AmiiboDto>
)
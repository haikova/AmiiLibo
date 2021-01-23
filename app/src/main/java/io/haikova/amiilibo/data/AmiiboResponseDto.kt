package io.haikova.amiilibo.data

import com.google.gson.annotations.SerializedName

data class AmiiboResponseDto(
  @SerializedName("amiibo") val amiibo: List<AmiiboDto>
)
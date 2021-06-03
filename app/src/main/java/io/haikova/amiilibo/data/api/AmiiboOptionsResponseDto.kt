package io.haikova.amiilibo.data.api

import com.google.gson.annotations.SerializedName

data class AmiiboOptionsResponseDto(
  @SerializedName("amiibo") val amiiboOptions: List<AmiiboOptionDto>
)
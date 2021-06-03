package io.haikova.amiilibo.data.api

import com.google.gson.annotations.SerializedName

data class AmiiboOptionDto(
  @SerializedName("key") val key: String,
  @SerializedName("name") val name: String
)
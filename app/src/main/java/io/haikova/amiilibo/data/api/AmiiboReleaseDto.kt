package io.haikova.amiilibo.data.api

import com.google.gson.annotations.SerializedName

data class AmiiboReleaseDto(
  @SerializedName("au") val au : String?,
  @SerializedName("eu") val eu : String?,
  @SerializedName("jp") val jp : String?,
  @SerializedName("na") val na : String?
)
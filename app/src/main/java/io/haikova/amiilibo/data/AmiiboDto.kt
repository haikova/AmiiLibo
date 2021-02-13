package io.haikova.amiilibo.data

import com.google.gson.annotations.SerializedName

data class AmiiboDto (
  @SerializedName("amiiboSeries") val amiiboSeries : String,
  @SerializedName("character") val character : String,
  @SerializedName("gameSeries") val gameSeries : String,
  @SerializedName("head") val head : String,
  @SerializedName("image") val image : String,
  @SerializedName("name") val name : String,
  @SerializedName("release") val release : AmiiboReleaseDto?,
  @SerializedName("tail") val tail : String,
  @SerializedName("type") val type : String
)
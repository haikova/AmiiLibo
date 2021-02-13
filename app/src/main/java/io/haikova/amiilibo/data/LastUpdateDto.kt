package io.haikova.amiilibo.data

import com.google.gson.annotations.SerializedName

data class LastUpdateDto (
  @SerializedName("lastUpdated") val lastUpdated: String
    )
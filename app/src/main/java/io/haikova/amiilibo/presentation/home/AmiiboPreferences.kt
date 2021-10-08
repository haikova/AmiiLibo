package io.haikova.amiilibo.presentation.home

import android.content.Context
import android.content.SharedPreferences

class AmiiboPreferences(
  context: Context
) {
  private val preferences: SharedPreferences =
    context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

  var lastDataUpdateCheck: String?
    get() = preferences.getString(LAST_DATA_UPDATE_CHECK, null)
    set(value) = preferences.edit().putString(LAST_DATA_UPDATE_CHECK, value).apply()

  var lastDataUpdate: String?
    get() = preferences.getString(LAST_DATA_UPDATE, null)
    set(value) = preferences.edit().putString(LAST_DATA_UPDATE, value).apply()

  private companion object {
    const val PREF_NAME = "io.haikova.amiilibo.AMIIBO_PREFERENCE"
    const val LAST_DATA_UPDATE = "Last data update"
    const val LAST_DATA_UPDATE_CHECK= "Last data update check"
  }
}
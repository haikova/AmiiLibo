package io.haikova.amiilibo

import android.app.Activity
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.hideKeyboard() {
  val inputMethodManager = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}
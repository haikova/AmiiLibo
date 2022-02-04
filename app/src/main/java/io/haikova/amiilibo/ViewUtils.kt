package io.haikova.amiilibo

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.hideKeyboard() {
  val inputMethodManager =
    this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

@ColorInt
fun Context.getColorFromAttr(@AttrRes attribute: Int) =
  TypedValue().let { theme.resolveAttribute(attribute, it, true); it.data }
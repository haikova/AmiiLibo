package io.haikova.amiilibo.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val ThemeLight = lightColors(
  primary = red_e1,
  primaryVariant = red_e1,
  onPrimary = white,
  secondary = gray_39,
  secondaryVariant = gray_39,
  onSecondary = black,
  onBackground = white
)

private val ThemeDark = darkColors(
  primary = red_db,
  primaryVariant = red_db,
  onPrimary = black,
  secondary = white,
  secondaryVariant = white,
  onSecondary = black,
  onBackground = black
)

@Composable
fun AppTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    ThemeDark
  } else {
    ThemeLight
  }
  CompositionLocalProvider {
    MaterialTheme(
      colors = colors,
      content = content
    )
  }
}

object AppTheme {

  val color: Colors
  @Composable
  get() = MaterialTheme.colors
}

package io.haikova.amiilibo.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.haikova.amiilibo.R

private val fonts = FontFamily(
  Font(R.font.poppins),
  Font(R.font.poppins_light, FontWeight.Light),
  Font(R.font.poppins_medium, FontWeight.Medium),
  Font(R.font.poppins_semibold, FontWeight.SemiBold)
)

val Heading1 = TextStyle(
  fontFamily = fonts,
  fontWeight = FontWeight.Medium,
  fontSize = 36.sp
)

val Caption4 = TextStyle(
  fontFamily = fonts,
  fontWeight = FontWeight.Light,
  fontSize = 16.sp
)

val Body1 = TextStyle(
  fontFamily = fonts,
  fontWeight = FontWeight.Normal,
  fontSize = 20.sp
)

val Button = TextStyle(
  fontFamily = fonts,
  fontWeight = FontWeight.Medium,
  fontSize = 18.sp
)
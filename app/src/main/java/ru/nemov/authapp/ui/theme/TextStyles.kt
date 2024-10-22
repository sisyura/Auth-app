package ru.nemov.authapp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.nemov.authapp.R

val robotoFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_medium, FontWeight.Medium)
)

val customTextStyle = TextStyle(
    color = Black,
    fontSize = 20.sp,
    fontWeight = FontWeight.Normal,
    letterSpacing = 0.41.sp,
    fontFamily = robotoFamily,
)

val title = TextStyle(
    color = Black,
    fontSize = 20.sp,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = 0.41.sp,
    fontFamily = robotoFamily,
)

val title1 = TextStyle(
    color = Black,
    fontSize = 20.sp,
    fontWeight = FontWeight.Normal,
    letterSpacing = 0.41.sp,
    fontFamily = robotoFamily,
)

val body1 = TextStyle(
    color = Black,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold,
    letterSpacing = 0.41.sp,
    fontFamily = robotoFamily,
)

val body2 = TextStyle(
    color = Black,
    fontSize = 14.sp,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = 0.41.sp,
    fontFamily = robotoFamily,
)

val btnContent = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.Normal,
    letterSpacing = 0.41.sp,
    fontFamily = robotoFamily,
)
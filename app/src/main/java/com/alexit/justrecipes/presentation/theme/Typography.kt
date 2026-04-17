package com.alexit.justrecipes.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.alexit.justrecipes.R

val NunitoFont = FontFamily(
    Font(R.font.nunito_extra_light, weight = FontWeight.ExtraLight, style = FontStyle.Normal),
    Font(R.font.nunito_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(R.font.nunito_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.nunito_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(R.font.nunito_semi_bold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(R.font.nunito_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(R.font.nunito_extra_bold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
    Font(R.font.nunito_black, weight = FontWeight.Black, style = FontStyle.Normal),
)
@Immutable
data class CustomTypography(
    val title1: TextStyle,
    val input1: TextStyle,
    val input2: TextStyle
)
val themeTypography = CustomTypography(
    title1 = TextStyle(
        fontFamily = NunitoFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    ),
    input1 = TextStyle(
        fontFamily = NunitoFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    input2 = TextStyle(
        fontFamily = NunitoFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        textDecoration = TextDecoration.Underline
    )

)

val LocalCustomTypography = staticCompositionLocalOf {
    themeTypography
}
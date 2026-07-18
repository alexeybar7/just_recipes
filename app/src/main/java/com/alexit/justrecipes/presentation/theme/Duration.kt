package com.alexit.justrecipes.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class CustomDuration(
    val notifyDuration: Long,
    val circleLoaderDuration: Int
)

val themeDuration = CustomDuration(
    notifyDuration = 3000L,
    circleLoaderDuration = 1400
)
val LocalCustomDuration = staticCompositionLocalOf {
    themeDuration
}
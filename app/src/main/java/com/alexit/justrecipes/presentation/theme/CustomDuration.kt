package com.alexit.justrecipes.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class CustomDuration(
    val notifyDuration: Long
)

val themeDuration = CustomDuration(
    notifyDuration = 3000L
)
val LocalCustomDuration = staticCompositionLocalOf {
    themeDuration
}
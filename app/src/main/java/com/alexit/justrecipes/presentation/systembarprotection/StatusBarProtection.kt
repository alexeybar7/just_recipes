package com.alexit.justrecipes.presentation.systembarprotection

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun StatusBarProtection(
    color: Color = JustRecipesTheme.colors.background1,
    heightProvider: () -> Float = calculateStatusHeight()
) {
    Canvas(Modifier.fillMaxSize()) {
        val calculatedHeight = heightProvider()
        drawRect(
            color = color,
            size = Size(size.width, calculatedHeight),
        )
    }
}

@Composable
fun calculateStatusHeight(): () -> Float {
    val statusBars = WindowInsets.statusBars
    val density = LocalDensity.current
    return { statusBars.getTop(density).times(1.2f) }
    }
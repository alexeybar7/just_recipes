package com.alexit.justrecipes.presentation.systembarprotection

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun NavigationBarProtection(
    color: Color = JustRecipesTheme.colors.bottomMenu,
    heightProvider: () -> Float = calculateBottomHeight(),
) {
    //val win = WindowInsets.navigationBars
    Canvas(Modifier.fillMaxSize()) {
        val calculatedHeight = heightProvider()

        drawRect(
            topLeft = Offset(x = 0f, y = size.height - calculatedHeight),
            color = color,
            size = Size(size.width, calculatedHeight)
        )
    }
}

@Composable
fun calculateBottomHeight(): () -> Float {
    val navigationBars = WindowInsets.navigationBars
    val density = LocalDensity.current
    return { navigationBars.getBottom(density).times(1.2f) }
}
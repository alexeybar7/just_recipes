package com.alexit.justrecipes.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun JustRecipesTheme(
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalCustomColors provides themeColors,
        LocalCustomDimension provides themeDimension,
        LocalCustomTypography provides themeTypography,
        content = content
    )
}

object JustRecipesTheme {
    val colors: CustomColors
        @ReadOnlyComposable
        @Composable
        get() = LocalCustomColors.current

    val dimensions: CustomDimension
        @ReadOnlyComposable
        @Composable
        get() = LocalCustomDimension.current

    val typography: CustomTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalCustomTypography.current
}

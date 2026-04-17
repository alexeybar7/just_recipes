package com.alexit.justrecipes.presentation.feature.bottommenu

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import com.alexit.justrecipes.presentation.navigation.RecipesDestinations
import com.alexit.justrecipes.presentation.components.CustomButton
import com.alexit.justrecipes.presentation.components.CustomButtonSelected
import com.alexit.justrecipes.presentation.components.dpToPx
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun BottomMenu(
    recipesBottomMenuScreens: List<RecipesDestinations>,
    buttonSelected: (RecipesDestinations) -> Unit,
    currentScreen: RecipesDestinations
) {
    val sizeWidth = sizeWidth()
    DrawBottonDividers(
        color = JustRecipesTheme.colors.dividerBottomMenu,
        strokeWidth = JustRecipesTheme.dimensions.thicknessDividerBottomMenu.dpToPx(),
        startX = sizeWidth.dpToPx(),
        startY = JustRecipesTheme.dimensions.heightDividerBottomMenu.dpToPx(),
        heightMenu = JustRecipesTheme.dimensions.heightBottomMenu.dpToPx()
    )

    recipesBottomMenuScreens.forEach { screen ->
        if (currentScreen == screen) {
            CustomButtonSelected(
                iconResId = screen.icon,
                iconDescription = screen.iconDescription,
                sizeWidth = sizeWidth,
                sizeHeight = JustRecipesTheme.dimensions.heightBottomMenu,
                sizeIcon = JustRecipesTheme.dimensions.sizeIcon1,
                colorBottomMenuSelected = JustRecipesTheme.colors.bottomMenuSelected,
                colorOnBottomMenuSelected = JustRecipesTheme.colors.onBottomMenuSelected,
                colorTopBorderButtonSelected = JustRecipesTheme.colors.topBorderButtonSelected,
                heightTopBorderButtonSelected = JustRecipesTheme.dimensions.heightTopBorderButtonSelected
            )
        } else {
            CustomButton(
                iconResId = screen.icon,
                iconDescription = screen.iconDescription,
                sizeWidth = sizeWidth,
                sizeHeight = JustRecipesTheme.dimensions.heightBottomMenu,
                sizeIcon = JustRecipesTheme.dimensions.sizeIcon1,
                colorOnBottomMenu = JustRecipesTheme.colors.onBottomMenu,
                onClick = { buttonSelected(screen) }
            )
        }
    }
}

@Composable
private fun sizeWidth () : Dp {
    val screenWidth = LocalWindowInfo.current.containerDpSize.width
    return screenWidth / 4
}

@Composable
private fun DrawBottonDividers(
    color: Color,
    strokeWidth: Float,
    startX: Float,
    startY: Float,
    heightMenu: Float
) {
    Canvas(modifier = Modifier) {
        drawLine(
            color = color,
            start = Offset(startX, - startY / 2),
            end = Offset(startX, startY / 2),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color,
            start = Offset(2 * startX, - startY / 2),
            end = Offset(2 * startX, startY / 2),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color,
            start = Offset(3 * startX, - startY / 2),
            end = Offset(3 * startX, startY / 2),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color,
            start = Offset(0f, heightMenu / 2),
            end = Offset(4 * startX, heightMenu / 2),
            strokeWidth = strokeWidth
        )
    }
}

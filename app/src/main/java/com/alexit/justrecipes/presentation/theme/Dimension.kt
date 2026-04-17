package com.alexit.justrecipes.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CustomDimension(
    val heightTitlePanel: Dp,
    val heightBottomMenu: Dp,
    val thicknessDividerBottomMenu: Dp,
    val heightDividerBottomMenu: Dp,
    val heightTopBorderButtonSelected: Dp,
    val paddingTextTitlePanel: Dp,
    val heightFieldInput: Dp,
    val widthInputtedIngredient: Dp,
    val widthInputtedIngredientField: Dp,
    val widthInputtedIngredientText: Dp,
    val widthInputtedIngredientWeight: Dp,
    val heightInputtedIngredientWeight: Dp,
    val paddingFieldInput: Dp,
    val contentPaddingField: Dp,
    val radiusCornerField: Dp,
    val borderThickness: Dp,
    val sizeIcon1: Dp,
    val sizeIcon2: Dp,
    val heightDialog: Dp,
    val widthDialog: Dp,
    val heightPopup: Dp,
    val widthPopup: Dp,
    val heightNewIngredientDialog: Dp,
    val widthNewIngredientDialog: Dp
)

val themeDimension = CustomDimension(
    heightTitlePanel = 88.dp,
    heightBottomMenu = 62.dp,
    thicknessDividerBottomMenu = 2.dp,
    heightDividerBottomMenu = 54.dp,
    heightTopBorderButtonSelected = 6.dp,
    paddingTextTitlePanel = 24.dp,
    heightFieldInput = 48.dp,
    widthInputtedIngredient = 328.dp,
    widthInputtedIngredientText = 170.dp,
    widthInputtedIngredientField = 292.dp,
    widthInputtedIngredientWeight = 70.dp,
    heightInputtedIngredientWeight = 30.dp,
    paddingFieldInput = 12.dp,
    contentPaddingField = 10.dp,
    radiusCornerField = 12.dp,
    borderThickness = 1.dp,
    sizeIcon1 = 32.dp,
    sizeIcon2 = 24.dp,
    heightDialog = 200.dp,
    widthDialog = 300.dp,
    heightPopup = 150.dp,
    widthPopup = 300.dp,
    heightNewIngredientDialog = 500.dp,
    widthNewIngredientDialog = 300.dp
)

val LocalCustomDimension = staticCompositionLocalOf {
    themeDimension
}

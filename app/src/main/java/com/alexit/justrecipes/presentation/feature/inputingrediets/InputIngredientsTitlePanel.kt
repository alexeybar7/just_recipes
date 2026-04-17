package com.alexit.justrecipes.presentation.feature.inputingrediets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Composable
fun InputIngredientsTitlePanel(
    height: Dp,
    background: Color,
    color: Color,
    padding: Dp,
    text: String,
    style: TextStyle
) {
    Row(
        modifier = Modifier
            .height(height)
            .background(background)
            .padding(padding)
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        BasicText(
            text = text,
            modifier = Modifier,
            style = style,
            color = { color },
        )
    }
}
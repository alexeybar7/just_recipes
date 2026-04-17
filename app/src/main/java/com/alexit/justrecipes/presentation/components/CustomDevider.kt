package com.alexit.justrecipes.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun CustomDivider (
    color: Color,
    thickness: Float,
    startX: Float,
    endX: Float,
    startY: Float,
    endY: Float
) {
    Canvas(modifier = Modifier) {
        drawLine(
            color = color,
            start = Offset(x = startX, y = startY),
            end = Offset(x = endX, y = endY),
            strokeWidth = thickness
        )
    }
}
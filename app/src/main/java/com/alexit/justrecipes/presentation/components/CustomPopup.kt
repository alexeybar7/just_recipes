package com.alexit.justrecipes.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomPopup(
    onDismissRequest: () -> Unit,
    contentPadding: Dp,
    widthPopup: Dp,
    colorBackground: Color,
    colorBorder: Color,
    colorText: Color,
    radiusShape: Dp,
    borderThickness: Dp,
    textStyle: TextStyle,
    textPopupPre: String,
    textPopupAft: String,
    item: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = onDismissRequest
                )
                .width(widthPopup)
                .background(
                    color = colorBackground,
                    shape = RoundedCornerShape(size = radiusShape)
                )
                .border(
                    border = BorderStroke(
                        width = borderThickness,
                        color = colorBorder
                    ),
                    shape = RoundedCornerShape(size = radiusShape)
                )
                .padding(contentPadding)
        ) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth(),
                style = textStyle.copy(
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = textStyle.fontSize
                ),
                color = { colorText },
                text = "$textPopupPre\n\r$item\n\r$textPopupAft"
            )
        }
    }
}

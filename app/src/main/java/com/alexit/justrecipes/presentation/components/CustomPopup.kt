package com.alexit.justrecipes.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import kotlinx.coroutines.delay

@Composable
fun CustomPopup(
    message: String,
    state: NotifyState,
    onDismissRequest: () -> Unit
) {
    val contentPadding = JustRecipesTheme.dimensions.paddingFieldInput
    val widthPopup = JustRecipesTheme.dimensions.widthPopup
    val radiusShape = JustRecipesTheme.dimensions.radiusCornerField
    val borderThickness = JustRecipesTheme.dimensions.borderThickness
    val textStyle = JustRecipesTheme.typography.text2
    val sizeIcon = JustRecipesTheme.dimensions.sizeIcon1
    val duration = JustRecipesTheme.duration.notifyDuration

    val appearance: NotifyAppearance = when (state) {
        NotifyState.INFO -> NotifyAppearance(
            colorBackground = JustRecipesTheme.colors.notifyBackgroundInfo,
            colorBorder = JustRecipesTheme.colors.notifyBackgroundInfo,
            colorText = JustRecipesTheme.colors.notifyTextInfo,
            icon = R.drawable.info_24px,
            contentDescription = R.string.icon_info
        )
        NotifyState.WARNING -> {NotifyAppearance(
            colorBackground = JustRecipesTheme.colors.notifyBackgroundWarning,
            colorBorder = JustRecipesTheme.colors.notifyBorderWarning,
            colorText = JustRecipesTheme.colors.notifyTextWarning,
            icon = R.drawable.error_24px,
            contentDescription = R.string.icon_error
        )}
        NotifyState.DANGER -> NotifyAppearance(
            colorBackground = JustRecipesTheme.colors.notifyBackgroundAlert,
            colorBorder = JustRecipesTheme.colors.notifyBorderAlert,
            colorText = JustRecipesTheme.colors.notifyTextAlert,
            icon = R.drawable.dangerous_24px,
            contentDescription = R.string.icon_dangerous
        )
    }

    val dropdownPopupPositioner = object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
        ): IntOffset {
            val x = (windowSize.width - popupContentSize.width) / 2
            val y = windowSize.height - popupContentSize.height
            return IntOffset(x, y)
        }
    }

    LaunchedEffect(Unit) {
        delay(duration)
        onDismissRequest()
    }
    Popup(
        onDismissRequest = onDismissRequest,
        popupPositionProvider = dropdownPopupPositioner
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = onDismissRequest
                )
                .width(widthPopup)
                .background(
                    color = appearance.colorBackground,
                    shape = RoundedCornerShape(size = radiusShape)
                )
                .border(
                    border = BorderStroke(
                        width = borderThickness,
                        color = appearance.colorBorder
                    ),
                    shape = RoundedCornerShape(size = radiusShape)
                )
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .size(sizeIcon),
                imageVector = ImageVector.vectorResource(appearance.icon),
                contentDescription = stringResource(appearance.contentDescription),
                colorFilter = ColorFilter.tint(appearance.colorText)
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth(),
                style = textStyle.copy(
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(
                    maxFontSize = textStyle.fontSize
                ),
                color = { appearance.colorText },
                text = message
            )
        }
    }
}

private data class NotifyAppearance(
    val colorBackground: Color,
    val colorBorder: Color,
    val colorText: Color,
    val icon: Int,
    val contentDescription: Int
)

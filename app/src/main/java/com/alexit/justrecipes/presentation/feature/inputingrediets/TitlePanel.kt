package com.alexit.justrecipes.presentation.feature.inputingrediets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexit.justrecipes.R
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun TitlePanel(
    text: String,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null
) {
    val height = JustRecipesTheme.dimensions.heightTitlePanel
    val background = JustRecipesTheme.colors.background1
    val color = JustRecipesTheme.colors.text1
    val padding = JustRecipesTheme.dimensions.paddingTextTitlePanel
    val style = JustRecipesTheme.typography.text1
    val iconLeftId = R.drawable.chevron_left_circle
    val iconLeftDescription = R.string.icon_arrow_back
    val sizeIcon = JustRecipesTheme.dimensions.sizeIcon3
    val heightTitleField = JustRecipesTheme.dimensions.heightTitleField

    Row(
        modifier = Modifier
            .height(height)
            .background(background)
            .padding(padding)
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier =
            Modifier
                .size(heightTitleField),
            contentAlignment = Alignment.CenterStart
        ) {
            if (onLeftClick != null) {
                Image(
                    modifier = Modifier
                        .size(sizeIcon)
                        .clickable(
                            enabled = true,
                            onClick = onLeftClick
                        ),
                    imageVector = ImageVector.vectorResource(iconLeftId),
                    contentDescription = stringResource(iconLeftDescription),
                    colorFilter = ColorFilter.tint(color)
                )
            }
        }
        Box(modifier =
            Modifier
                .weight(1f)
                .height(heightTitleField),
            contentAlignment = Alignment.Center
        ){
            BasicText(
                text = text,
                modifier = Modifier,
                style = style,
                color = { color },
            )
        }
        Box(modifier =
                Modifier
                    .size(heightTitleField),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (onRightClick != null) {
                Image(
                    modifier = Modifier
                        .size(sizeIcon)
                        .clickable(
                            enabled = true,
                            onClick = onRightClick
                        ),
                    imageVector = ImageVector.vectorResource(iconLeftId),
                    contentDescription = stringResource(iconLeftDescription),
                    colorFilter = ColorFilter.tint(color)
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TestTitlePanel(){
    JustRecipesTheme {
        TitlePanel(
            text = "Поиск рецепта",
            onLeftClick = {},
            onRightClick = {}
        )
    }
}
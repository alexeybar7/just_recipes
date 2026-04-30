package com.alexit.justrecipes.presentation.feature.inputingrediets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import com.alexit.justrecipes.domain.model.CategoryModel
import com.alexit.justrecipes.presentation.components.CustomDivider
import com.alexit.justrecipes.presentation.components.dpToPx
import kotlinx.collections.immutable.PersistentList

@Composable
fun MakeNewIngredient(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    heightDialog: Dp,
    widthDialog: Dp,
    colorBackground: Color,
    colorStroke: Color,
    colorText: Color,
    radiusShape: Dp,
    borderThickness: Dp,
    textDialogPre: String,
    item: String,
    textDialogAft: String,
    textStyle: TextStyle,
    textStyleCategory: TextStyle,
    textConfirmation: String,
    textDismiss: String,
    contentPadding: Dp,
    colorBackgroundCategory: Color,
    colorBorderCategory: Color,
    listCategory: PersistentList<CategoryModel>,
    colorTextCategory: Color,
    colorBackgroundCategorySelected: Color
) {
    val selectedId = remember {  mutableIntStateOf(-1) }
    Dialog(onDismissRequest = onDismissRequest ) {
        Column(
            modifier = Modifier
                .size(
                    height = heightDialog,
                    width = widthDialog
                )
                .background(
                    color = colorBackground,
                    shape = RoundedCornerShape(size = radiusShape)
                )
                .border(
                    border = BorderStroke(
                        width = borderThickness,
                        color = colorStroke
                    ),
                    shape = RoundedCornerShape(size = radiusShape)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicText(
                style = textStyle.copy(
                    textAlign = TextAlign.Center
                ),
                color = { colorText },
                text = "$textDialogPre\n\r$item?\n\r$textDialogAft"
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(
                        fill = true,
                        weight = 1f
                    )
                    .background(
                        color = colorBackgroundCategory,
                        shape = RoundedCornerShape(size = radiusShape)
                    )
                    .border(
                        border = BorderStroke(
                            width = borderThickness,
                            color = colorBorderCategory
                        )
                    )
                    .padding(start = contentPadding, end = contentPadding),
                verticalArrangement = Arrangement.Center
            ) {
                items(items = listCategory, key = { it.id }) { category ->
                    BasicText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (category.id == selectedId.intValue)
                                    colorBackgroundCategorySelected
                                else colorBackgroundCategory
                            )
                            .selectable(
                                selected = category.id == selectedId.intValue,
                                onClick = {
                                    if (selectedId.intValue != category.id)
                                    { selectedId.intValue = category.id }
                                    else
                                    { selectedId.intValue = -1 }
                                })
                            .padding(contentPadding),
                        text = category.category,
                        style = textStyleCategory,
                        color = { colorTextCategory }
                    )
                    CustomDivider(
                        color = colorTextCategory,
                        thickness = borderThickness.dpToPx(),
                        startX = 0f,
                        endX = (widthDialog - contentPadding * 2).dpToPx(),
                        startY = 0f,
                        endY = 0f
                    )
                }
            }
            Row(
                modifier = Modifier
                    .height(heightDialog / 10)
                    .border(
                        border = BorderStroke(
                            width = borderThickness,
                            color = colorStroke
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = radiusShape,
                            bottomEnd = radiusShape
                        )
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            onClick = onDismissRequest
                        )
                        .width(widthDialog / 2)
                        .fillMaxHeight()
                        .wrapContentHeight(),
                    style = textStyle.copy(
                        textAlign = TextAlign.Center
                    ),
                    color = { colorText },
                    text = textDismiss
                )
                CustomDivider(
                    color = colorTextCategory,
                    thickness = borderThickness.dpToPx(),
                    startX = 0f,
                    endX = 0f,
                    startY = ((heightDialog / 10) / 2).dpToPx(),
                    endY = - ((heightDialog / 10) / 2).dpToPx()
                )
                BasicText(
                    modifier = Modifier
                        .alpha( if (selectedId.intValue > -1) 1f else 0.2f )
                        .clickable(
                            enabled = true,
                            onClick = {
                                if (selectedId.intValue > -1) onConfirmation(listCategory.find {
                                    it.id == selectedId.intValue }!!.category)
                            }
                        )
                        .width(widthDialog / 2)
                        .fillMaxHeight()
                        .wrapContentHeight(),
                    style = textStyle.copy(
                        textAlign = TextAlign.Center
                    ),
                    color =  { colorText },
                    text = textConfirmation
                )
            }
        }
    }
}

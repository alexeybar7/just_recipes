package com.alexit.justrecipes.presentation.feature.requestai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.domain.model.IngredientInputedModel
import com.alexit.justrecipes.presentation.components.CircleLoader
import com.alexit.justrecipes.presentation.components.CustomDivider
import com.alexit.justrecipes.presentation.components.CustomPopup
import com.alexit.justrecipes.presentation.components.TitlePanel
import com.alexit.justrecipes.presentation.components.dpToPx
import com.alexit.justrecipes.presentation.feature.requestai.viewmodel.RequestAiViewModel
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlin.collections.contains

@Composable
fun RequestAiScreen(
    requestAiViewModel: RequestAiViewModel = hiltViewModel(),
    onPromptClick: (String) -> Unit
) {
    val requestAiUiState by requestAiViewModel.uiState.collectAsStateWithLifecycle()
    val inputtedIngredientsState = requestAiViewModel.inputtedIngredientsState.collectAsStateWithLifecycle()

    val colorText = JustRecipesTheme.colors.text4
    val styleText = JustRecipesTheme.typography.text1
    val padding = JustRecipesTheme.dimensions.gap1

    var isNewNotify by remember { mutableStateOf(false) }
    var notifyMessage by remember { mutableStateOf("") }
    var notifyState by remember { mutableStateOf(NotifyState.INFO) }

    if (isNewNotify) {
        CustomPopup(
            message = notifyMessage,
            state = notifyState,
            onDismissRequest = { isNewNotify = !isNewNotify }
        )
    }

    var isDishFirst by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TitlePanel(
            text = stringResource(R.string.title_request_ai),
            onRightClick = onPromptClick,
            textRight = stringResource(R.string.request)
        )
        Column(
            modifier = Modifier
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BasicText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.make_recipe_from),
                style = styleText,
                color = { colorText }
            )
            Column(modifier = Modifier.weight(1f)) {
                when (val sourceState = inputtedIngredientsState.value) {
                    is SourceState.Loading -> LoadingScreen()
                    is SourceState.Success -> ShowIngredients(sourceState.data.toPersistentList())
                    is SourceState.Error -> {
                        isNewNotify = true
                        notifyMessage = if (sourceState.message != null) {
                            "${stringResource(R.string.hardware_error)}\n${sourceState.message}"
                        } else {
                            stringResource(R.string.unknown_error_occurred)
                        }
                        notifyState = NotifyState.DANGER
                    }
                }
            }
            SelectTypeDish(isDishFirst, onSelect = { first: Boolean -> isDishFirst = first })
        }
    }
}


@Composable
private fun LoadingScreen() {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircleLoader(
            color = JustRecipesTheme.colors.circleLoader,
            modifier = Modifier.size(JustRecipesTheme.dimensions.sizeCircleLoader),
            isVisible = isLoading
        )
    }
}

@Composable
private fun ShowIngredients (inputtedIngredients: PersistentList<IngredientInputedModel>) {
    val liquid = stringArrayResource(R.array.liquid_foodstuff)
    val piece = stringArrayResource(R.array.piece_foodstuff)
    val colorText = JustRecipesTheme.colors.text4
    val styleText = JustRecipesTheme.typography.text1
    val styleDigit = JustRecipesTheme.typography.text8
    val widthIngredientName = JustRecipesTheme.dimensions.widthIngredientNameInRequest
    val padding = JustRecipesTheme.dimensions.gap1

    LazyColumn() {
        itemsIndexed(
            items = inputtedIngredients,
            key = { _ , item -> item.id }
        ) { index, ingredient ->
            Row(
                modifier = Modifier
                    .padding(top = padding, bottom = padding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicText(
                    modifier = Modifier
                        .width(widthIngredientName),
                    text = "${index + 1}. ${ingredient.name.replaceFirstChar { it.uppercase() }}",
                    style = styleText.copy(textAlign = TextAlign.Left),
                    color = { colorText }
                )
                if (ingredient.weight != null) {
                    val unit: String = if (liquid.contains(ingredient.category)) {
                        stringResource(R.string.ml)
                    } else if (piece.contains(ingredient.category)) {
                        stringResource(R.string.piece)
                    } else {
                        stringResource(R.string.g)
                    }
                    BasicText(
                        text = "${ingredient.weight} $unit",
                        style = styleDigit,
                        color = { colorText }
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectTypeDish(isDishFirst: Boolean, onSelect: (Boolean) -> Unit) {
    val heightSelector = JustRecipesTheme.dimensions.heightSelectorDish
    val widthSelector = JustRecipesTheme.dimensions.widthSelectorDish
    val padding = JustRecipesTheme.dimensions.gap1
    val colorBackground = JustRecipesTheme.colors.background4
    val colorBackgroundSelected = JustRecipesTheme.colors.background3
    val colorBorder = JustRecipesTheme.colors.text4
    val colorText = JustRecipesTheme.colors.text4
    val radiusShape = JustRecipesTheme.dimensions.radiusCornerField
    val borderThickness = JustRecipesTheme.dimensions.borderThickness
    val textStyle = JustRecipesTheme.typography.text1
    val textFirstDish = stringResource(R.string.first_dish)
    val textSecondDish = stringResource(R.string.second_dish)
    Row(
        modifier = Modifier
            .padding(padding)
            .height(heightSelector)
            .width(widthSelector),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            modifier = Modifier
                .clip(RoundedCornerShape(
                    topStart = radiusShape,
                    bottomStart = radiusShape))
                .background(color = if (isDishFirst) colorBackgroundSelected else colorBackground)
                .alpha( if (isDishFirst) 1f else 0.3f )
                .clickable(
                    enabled = true,
                    onClick = { onSelect(true) }
                )
                .width(widthSelector / 2)
                .fillMaxHeight()
                .wrapContentHeight(),
            style = textStyle.copy(
                textAlign = TextAlign.Center
            ),
            color = { colorText },
            text = textFirstDish
        )

        CustomDivider(
            color = colorBorder,
            thickness = borderThickness.dpToPx(),
            startX = 0f,
            endX = 0f,
            startY = (heightSelector / 2).dpToPx(),
            endY = - (heightSelector / 2).dpToPx()
        )

        BasicText(
            modifier = Modifier
                .clip(RoundedCornerShape(
                    topEnd = radiusShape,
                    bottomEnd = radiusShape))
                .background(color = if (!isDishFirst) colorBackgroundSelected else colorBackground)
                .alpha( if (!isDishFirst) 1f else 0.3f )
                .clickable(
                    enabled = true,
                    onClick = { onSelect(false) }
                )
                .width(widthSelector / 2)
                .fillMaxHeight()
                .wrapContentHeight(),
            style = textStyle.copy(
                textAlign = TextAlign.Center
            ),
            color = { colorText },
            text = textSecondDish
        )
    }
}
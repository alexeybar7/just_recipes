package com.alexit.justrecipes.presentation.feature.requestai

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.domain.model.IngredientInputedModel
import com.alexit.justrecipes.presentation.components.CircleLoader
import com.alexit.justrecipes.presentation.components.CustomPopup
import com.alexit.justrecipes.presentation.components.TitlePanel
import com.alexit.justrecipes.presentation.feature.requestai.viewmodel.RequestAiViewModel
//import com.alexit.justrecipes.ui.components.CustomText
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlin.collections.contains

@Composable
fun RequestAiScreen(
    requestAiViewModel: RequestAiViewModel = hiltViewModel(),
    onPromptClick: (String) -> Unit
) {
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

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TitlePanel(
            text = stringResource(R.string.title_request_ai)
        )
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            BasicText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.make_recipe_from),
                style = styleText,
                color = { colorText }
            )
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

    LazyColumn(
        modifier = Modifier
            .animateContentSize()
    ) {
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
package com.alexit.justrecipes.presentation.feature.searchrecipes

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.presentation.components.CircleLoader
import com.alexit.justrecipes.presentation.components.CustomPopup
import com.alexit.justrecipes.presentation.components.CustomScrollBar
import com.alexit.justrecipes.presentation.components.CustomTextField
import com.alexit.justrecipes.presentation.components.TitlePanel
import com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel.SearchRecipesViewModel
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun SearchRecipesScreen(
    searchRecipesViewModel: SearchRecipesViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    val searchRecipesUiState by searchRecipesViewModel.uiState.collectAsStateWithLifecycle()
    val recipesPagingDataCard = searchRecipesViewModel.recipeCardData.collectAsLazyPagingItems()

    val paddingFieldInput = JustRecipesTheme.dimensions.paddingFieldInput
    val innerPaddingCard = JustRecipesTheme.dimensions.innerPaddingCard

    if (searchRecipesUiState.isNewNotify) {
        CustomPopup(
            message = searchRecipesUiState.notifyMessage,
            state = NotifyState.DANGER,
            onDismissRequest = { searchRecipesViewModel.notifyDismiss() }
        )
    }

    Column(modifier = Modifier.fillMaxSize()
    ) {
        TitlePanel(
            text = stringResource(R.string.title_search_recipe),
        )
        Column(
            modifier = Modifier
                .padding(vertical = paddingFieldInput)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                state = searchRecipesViewModel.inputTextState,
                iconPlaceholder = R.drawable.text_search,
                iconDescriptionPlaceholder = R.string.icon_text_search,
                placeholder = stringResource(R.string.placeholder_search_recipes),
            )

            val listCardState = rememberLazyListState()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(start = innerPaddingCard)
                        .animateContentSize(),
                    state = listCardState
                ) {
                    items(count = recipesPagingDataCard.itemCount,
                        key = recipesPagingDataCard.itemKey { "${it.id}_${it.name}_${it.image}" }
                    ) { index ->
                        recipesPagingDataCard[index]?.let {
                            RecipeCardItem(
                                recipe = it,
                                onRecipeClick = onRecipeClick
                            )
                        }
                        recipesPagingDataCard.apply {
                            if (recipesPagingDataCard.loadState.append is LoadState.Loading ||
                                recipesPagingDataCard.loadState.refresh is LoadState.Loading ||
                                recipesPagingDataCard.loadState.prepend is LoadState.Loading
                                ) {
                                LoadingScreen()
                            }
                            if (recipesPagingDataCard.loadState.append is LoadState.Error ||
                                recipesPagingDataCard.loadState.refresh is LoadState.Error ||
                                recipesPagingDataCard.loadState.prepend is LoadState.Error
                                ) {
                                val error = if (loadState.append is LoadState.Error) {
                                    (loadState.append as LoadState.Error).error
                                } else if (loadState.refresh is LoadState.Error) {
                                    (loadState.refresh as LoadState.Error).error
                                } else {
                                    (loadState.prepend as LoadState.Error).error
                                }
                                val errorMessage = if (error.message != null) {
                                    "${stringResource(R.string.hardware_error)}\n${error.message}"
                                } else {
                                    stringResource(R.string.unknown_error_occurred)
                                }
                                searchRecipesViewModel.notifyShow(message = errorMessage)
                            }

                        }
                    }
                }
                CustomScrollBar(
                    listState = listCardState,
                )
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

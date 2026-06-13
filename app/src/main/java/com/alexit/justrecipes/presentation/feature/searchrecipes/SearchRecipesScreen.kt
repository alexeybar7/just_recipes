package com.alexit.justrecipes.presentation.feature.searchrecipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.presentation.components.CircleLoader
import com.alexit.justrecipes.presentation.components.CustomTextField
import com.alexit.justrecipes.presentation.components.TitlePanel
import com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel.SearchRecipesViewModel
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun SearchRecipesScreen(
    searchRecipesViewModel: SearchRecipesViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    val recipeCardDataSource = searchRecipesViewModel.recipeCardData.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()
    ) {
        TitlePanel(
            text = stringResource(R.string.title_search_recipe),
        )
        Column(
            modifier = Modifier
                .padding(vertical = JustRecipesTheme.dimensions.paddingFieldInput)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                state = searchRecipesViewModel.inputTextState,
                iconPlaceholder = R.drawable.text_search,
                iconDescriptionPlaceholder = R.string.icon_text_search,
                placeholder = stringResource(R.string.placeholder_search_recipes),
            )


            when (val sourceState = recipeCardDataSource.value) {
                is SourceState.Loading -> LoadingScreen()
                is SourceState.Success -> ShowListRecipes(
                    recipesCardData = sourceState.data,
                    onRecipeClick = onRecipeClick
                )
                is SourceState.Error -> ErrorScreen()
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
fun ErrorScreen() {}

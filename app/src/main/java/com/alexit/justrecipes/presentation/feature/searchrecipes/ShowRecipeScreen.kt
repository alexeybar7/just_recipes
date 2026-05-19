package com.alexit.justrecipes.presentation.feature.searchrecipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexit.justrecipes.R
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun ShowRecipeScreen(recipeId: Int, onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BasicText(
            text = stringResource(R.string.title_show_recipe),
            style = JustRecipesTheme.typography.text1,
            //color = JustRecipesTheme.colors.onTitlePanel
        )
    }
}
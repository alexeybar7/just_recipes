package com.alexit.justrecipes.presentation.feature.searchrecipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexit.justrecipes.R
//import com.alexit.justrecipes.ui.components.CustomText
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun SearchRecipeScreen() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BasicText(
            text = stringResource(R.string.title_search_recipe),
            style = JustRecipesTheme.typography.title1,
            //color = JustRecipesTheme.colors.onTitlePanel
        )
    }
}
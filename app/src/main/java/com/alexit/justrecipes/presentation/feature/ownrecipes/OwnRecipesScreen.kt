package com.alexit.justrecipes.presentation.feature.ownrecipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexit.justrecipes.R
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun OwnRecipesScreen(onAddRecipeClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        BasicText(
            text = stringResource(R.string.title_own_recipes),
            style = JustRecipesTheme.typography.text1,
            //color = JustRecipesTheme.colors.onTitlePanel
        )
    }
}

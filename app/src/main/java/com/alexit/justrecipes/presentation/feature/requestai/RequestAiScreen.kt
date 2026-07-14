package com.alexit.justrecipes.presentation.feature.requestai

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexit.justrecipes.R
import com.alexit.justrecipes.presentation.components.TitlePanel
//import com.alexit.justrecipes.ui.components.CustomText
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun RequestAiScreen(onPromptClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()
    ) {
        TitlePanel(
            text = stringResource(R.string.title_request_ai)
        )
    }
}
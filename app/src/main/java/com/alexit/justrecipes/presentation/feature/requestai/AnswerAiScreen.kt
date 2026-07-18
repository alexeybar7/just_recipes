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
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun AnswerAiScreen(prompt: String, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TitlePanel(
            text = stringResource(R.string.title_answer_ai),
            onLeftClick = onBackClick,
            textLeft = stringResource(R.string.go_back),
            //onRightClick = onPromptClick,
            textRight = stringResource(R.string.save)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            BasicText(
                text = prompt,
                style = JustRecipesTheme.typography.text1,
                //color = JustRecipesTheme.colors.onTitlePanel
            )
        }
    }
}
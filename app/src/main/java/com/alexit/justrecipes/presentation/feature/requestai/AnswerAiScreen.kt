package com.alexit.justrecipes.presentation.feature.requestai

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.domain.model.ai.ResponseAi
import com.alexit.justrecipes.presentation.components.CircleLoader
import com.alexit.justrecipes.presentation.components.CustomPopup
import com.alexit.justrecipes.presentation.components.NotifySideEffect
import com.alexit.justrecipes.presentation.components.TitlePanel
import com.alexit.justrecipes.presentation.feature.requestai.viewmodel.AnswerAiViewModel
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AnswerAiScreen(
    answerAiViewModel: AnswerAiViewModel = hiltViewModel(),
    prompt: String,
    onBackClick: () -> Unit
) {
    val recipeAiNullable by answerAiViewModel.recipeAiState

    val scrollState = rememberScrollState()

    var isNewNotify by remember { mutableStateOf(false) }
    var notifyMessage by remember { mutableStateOf("") }
    var notifyState by remember { mutableStateOf(NotifyState.INFO) }
    val context = LocalContext.current

    LaunchedEffect(prompt) {
        answerAiViewModel.getRecipeAi(prompt)
        answerAiViewModel.sideEffect.collectLatest { notify ->
            when (notify) {
                is NotifySideEffect.ShowNotify -> {
                    isNewNotify = true
                    notifyMessage = "${notify.message.asString(context)}\n${notify.addition}".trimEnd()
                    notifyState = notify.state
                }
            }
        }
    }

    if (isNewNotify) {
        CustomPopup(
            message = notifyMessage,
            state = notifyState,
            onDismissRequest = { isNewNotify = !isNewNotify }
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize(),
    ) {
        TitlePanel(
            text = stringResource(R.string.title_answer_ai),
            onLeftClick = onBackClick,
            textLeft = stringResource(R.string.go_back),
            //onRightClick = onPromptClick,
            textRight = stringResource(R.string.save)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            //contentAlignment = Alignment.Center
        ) {
            if (recipeAiNullable == null) LoadingScreen()
            else {
                val answerAi: ResponseAi = recipeAiNullable!!
                val recipeAi: String = answerAi.choices.firstOrNull()?.message?.content ?: "EMPTY"

                BasicText(
                    text = recipeAi,
                    style = JustRecipesTheme.typography.text1,
                    //color = JustRecipesTheme.colors.onTitlePanel
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
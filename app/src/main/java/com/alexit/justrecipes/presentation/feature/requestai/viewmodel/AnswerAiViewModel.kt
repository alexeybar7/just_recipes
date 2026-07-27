package com.alexit.justrecipes.presentation.feature.requestai.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.common.StringResourceHolder
import com.alexit.justrecipes.domain.model.ai.ResponseAi
import com.alexit.justrecipes.domain.usecase.GetRecipeAiUseCase
import com.alexit.justrecipes.presentation.components.NotifySideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AnswerAiViewModel @Inject constructor(
    private val getRecipeAiUseCase: GetRecipeAiUseCase
) : ViewModel() {
    private val _recipeAiState = mutableStateOf<ResponseAi?>(null)
    val recipeAiState: MutableState<ResponseAi?> = _recipeAiState

    private val _sideEffect = Channel<NotifySideEffect>()
    val sideEffect: Flow<NotifySideEffect> = _sideEffect.receiveAsFlow()


    fun getRecipeAi(prompt: String) {
        if (recipeAiState.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val recipeAiAnswer = withContext(Dispatchers.IO) { getRecipeAiUseCase(prompt) }
                    when (recipeAiAnswer.status.value) {
                        in 200..299 -> {
                            val recipeAi = recipeAiAnswer.body<ResponseAi>()
                            _recipeAiState.value = recipeAi
                        }
                    }
                } catch (err: Throwable) {
                    _sideEffect.send(
                        NotifySideEffect.ShowNotify(
                            message = StringResourceHolder.StringResource(R.string.ai_not_avialable),
                            addition = err.message.toString(),
                            state = NotifyState.WARNING
                        )
                    )
                }
            }
        }
    }
}
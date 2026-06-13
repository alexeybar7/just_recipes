package com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.common.customDebounce
import com.alexit.justrecipes.domain.model.RecipeCardModel
import com.alexit.justrecipes.domain.usecase.GetRecipeCardDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor (
    private val getRecipeCardDataUseCase: GetRecipeCardDataUseCase,
) : ViewModel() {

    val inputTextState = TextFieldState()

    val recipeCardData: StateFlow<SourceState<List<RecipeCardModel>>>  =
        snapshotFlow { inputTextState.text }
            .customDebounce(300)
            .distinctUntilChanged()
            .customFlatMapLatest{ query ->
                getRecipeCardDataUseCase(query.toString())
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                SourceState.Loading
            )
}

fun <T, R> Flow<T>.customFlatMapLatest(transform: suspend (T) -> Flow<R>): Flow<R> = channelFlow {
    var previousJob: Job? = null
    collect { value ->
        previousJob?.cancel()
        previousJob = launch {
            transform(value).collect { send(it) }
        }
    }
}
package com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexit.justrecipes.common.customDebounce
import com.alexit.justrecipes.common.customFlatMapLatest
import com.alexit.justrecipes.domain.model.RecipeCardModel
import com.alexit.justrecipes.domain.usecase.GetRecipeCardDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(
    private val getRecipeCardDataUseCase: GetRecipeCardDataUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchRecipesUiState())
    val uiState: StateFlow<SearchRecipesUiState> = _uiState.asStateFlow()
    val inputTextState = TextFieldState()

    val recipeCardData: Flow<PagingData<RecipeCardModel>> =
        snapshotFlow { inputTextState.text }
            .customDebounce(300)
            .distinctUntilChanged()
            .customFlatMapLatest { query ->
                getRecipeCardDataUseCase(query.toString())
            }.cachedIn(viewModelScope)

    fun notifyShow(message: String) {
        _uiState.update { currentState ->
            currentState.copy(
                isNewNotify = true,
                notifyMessage = message
            )
        }
    }
    fun notifyDismiss() {
        _uiState.update { currentState ->
            currentState.copy(
                isNewNotify = false,
                notifyMessage = ""
            )
        }
    }
}

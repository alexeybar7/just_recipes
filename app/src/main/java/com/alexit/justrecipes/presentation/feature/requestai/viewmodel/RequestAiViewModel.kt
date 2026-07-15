package com.alexit.justrecipes.presentation.feature.requestai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.domain.model.IngredientInputedModel
import com.alexit.justrecipes.domain.usecase.GetInputtedIngredientsUseCase
import com.alexit.justrecipes.domain.usecase.GetRecipeFullDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RequestAiViewModel @Inject constructor(
    private val getInputtedIngredientsUseCase: GetInputtedIngredientsUseCase
) : ViewModel() {

    val inputtedIngredientsState: StateFlow<SourceState<List<IngredientInputedModel>>> by lazy {
        getInputtedIngredientsUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SourceState.Loading
        )
    }
}
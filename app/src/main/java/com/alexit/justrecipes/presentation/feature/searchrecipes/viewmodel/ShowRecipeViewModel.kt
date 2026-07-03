package com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.domain.model.RecipeModelFull
import com.alexit.justrecipes.domain.usecase.GetRecipeFullDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowRecipeViewModel @Inject constructor(
    private val getRecipeFullDataUseCase: GetRecipeFullDataUseCase
) : ViewModel() {

    private val _recipeState = MutableStateFlow<RecipeModelFull?>(null)
    val recipeState: StateFlow<RecipeModelFull?> = _recipeState.asStateFlow()

    fun getRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = getRecipeFullDataUseCase(recipeId)
            _recipeState.value = recipe
        }
    }
}
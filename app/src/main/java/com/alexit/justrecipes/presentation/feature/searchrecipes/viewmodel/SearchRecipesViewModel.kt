package com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.data.local.room.Relations.RecipeWithIngredients
import com.alexit.justrecipes.domain.model.RecipeCardModel
import com.alexit.justrecipes.domain.usecase.GetRecipeCardDataUseCase
import com.alexit.justrecipes.domain.usecase.GetRecipesWithIngredientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class SearchRecipesViewModel @Inject constructor (
    private val getRecipesWithIngredientsUseCase: GetRecipesWithIngredientsUseCase,
    private val getRecipeCardDataUseCase: GetRecipeCardDataUseCase
) : ViewModel() {

    val recipesWithIngredients: StateFlow<SourceState<List<RecipeWithIngredients>>> =
        getRecipesWithIngredientsUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SourceState.Loading
        )

    val recipeCardData: StateFlow<SourceState<List<RecipeCardModel>>> =
        getRecipeCardDataUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SourceState.Loading
        )

    val inputTextStateIngredient = TextFieldState()

    fun handleIntent(intent: SearchRecipeIntent) {
        when (intent) {
            is SearchRecipeIntent.SelectRecipe -> selectRecipe(intent.recipeName)
        }
    }

    private fun selectRecipe(recipeName: String) {}
}
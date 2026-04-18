package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import com.alexit.justrecipes.domain.model.IngredientModel

sealed class InputIngredientsIntent {
    data object LoadIngredients : InputIngredientsIntent()
    data object LoadInputtedIngredients : InputIngredientsIntent()
    data class SelectSuggestionIngredient(val suggestion: String) : InputIngredientsIntent()
    data class CheckingSelectedIngredient(val ingredientName: String) : InputIngredientsIntent()
    data object IsIngredientInputted : InputIngredientsIntent()
    data class AddNewIngredient(val ingredientCategory: String) : InputIngredientsIntent()
    data class AddInputtedIngredient(val ingredientId: Int) : InputIngredientsIntent()
    data class IsRemoveIngredient(val ingredient: IngredientModel) : InputIngredientsIntent()
    data object RemoveInputtedIngredient() : InputIngredientsIntent()
    data class ChangeWeightIngredient(val ingredientId: Int, val ingredientWeight: Int) : InputIngredientsIntent()
}

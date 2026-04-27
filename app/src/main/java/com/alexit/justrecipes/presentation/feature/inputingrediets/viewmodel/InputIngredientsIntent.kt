package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import com.alexit.justrecipes.domain.model.IngredientModel

sealed class InputIngredientsIntent {
    data class SelectSuggestionIngredient(val suggestion: String) : InputIngredientsIntent()
    data class CheckingSelectedIngredient(val ingredientName: String) : InputIngredientsIntent()
    data object IsIngredientInputted : InputIngredientsIntent()
    data class AddNewIngredient(val ingredientCategory: String) : InputIngredientsIntent()
    data object DismissNewIngredient: InputIngredientsIntent()
    data class IsRemoveIngredient(val ingredient: IngredientModel) : InputIngredientsIntent()
    data object RemoveInputtedIngredient : InputIngredientsIntent()
    data object DismissRemoveIngredient : InputIngredientsIntent()
    data class ChangeWeightIngredient(val ingredientId: Int, val ingredientWeight: Int) : InputIngredientsIntent()
}

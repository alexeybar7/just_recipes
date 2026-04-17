package com.alexit.justrecipes.presentation.feature.inputingrediets

data class InputIngredientsUiState(
    val isDeleteIngredient: Boolean = false,
    val isIngredientInputted: Boolean = false,
    val isIngredientNew: Boolean = false,
)

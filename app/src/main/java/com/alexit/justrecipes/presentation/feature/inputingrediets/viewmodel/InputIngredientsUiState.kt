package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import com.alexit.justrecipes.domain.model.IngredientModel

data class InputIngredientsUiState(
    val isIngredientInputted: Boolean = false,
    val alreadyInputtedIngredientName: String = "",
    val isIngredientNew: Boolean = false,
    val newIngredientId: Int = -1,
    val newIngredientName: String = "",
    val selectedIndexCategory: Int = -1,
    val isDeleteIngredient: Boolean = false,
    val deletingIngredientId: Int = -1,
    val deletingIngredientName: String = ""
)

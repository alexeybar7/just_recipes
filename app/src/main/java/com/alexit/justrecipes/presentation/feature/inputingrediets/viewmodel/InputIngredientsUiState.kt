package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import com.alexit.justrecipes.domain.model.CategoryModel
import com.alexit.justrecipes.domain.model.IngredientModel

data class InputIngredientsUiState(
    val isIngredientsNameLoading: Boolean = false,
    val ingredientsName: List<String> = emptyList(),
    val isInputtedIngredientsLoading: Boolean = false,
    val inputtedIngredients: List<IngredientModel> = emptyList(),
    val isIngredientInputted: Boolean = false,
    val alreadyInputtedIngredientName: String = "",
    val isIngredientNew: Boolean = false,
    val newIngredientName: String = "",
    val isDeleteIngredient: Boolean = false,
    val deletingIngredientId: Int = -1,
    val deletingIngredientName: String = "",
    val categories: List<CategoryModel> = emptyList()
)

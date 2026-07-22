package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import com.alexit.justrecipes.domain.model.database.CategoryModel

data class InputIngredientsUiState(
    val isIngredientNew: Boolean = false,
    val newIngredientName: String = "",
    val isDeleteIngredient: Boolean = false,
    val deletingIngredientId: Int = -1,
    val deletingIngredientName: String = "",
    val categories: List<CategoryModel> = emptyList()
)

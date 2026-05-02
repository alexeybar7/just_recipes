package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

sealed class InputIngredientsSideEffect {
    data class ShowToast(val message: String) : InputIngredientsSideEffect()
}
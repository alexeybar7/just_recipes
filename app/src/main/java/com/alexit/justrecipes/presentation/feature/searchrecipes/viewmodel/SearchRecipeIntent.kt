package com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel

sealed class SearchRecipeIntent {
    data class SelectRecipe(val recipeName: String) : SearchRecipeIntent()
}
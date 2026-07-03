package com.alexit.justrecipes.domain.model

data class IngredientDataModel(
    val id: Int,
    val name: String,
    val quantity: Double?,
    val unit: String,
    val density: Double?,
    val weight: Int?,
    val isInputted: Boolean
)

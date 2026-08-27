package com.alexit.justrecipes.domain.model.database

data class RecipeCardModel(
    val id: Int,
    val name: String,
    val portion: Int?,
    val image: String,
    val duration: Int?,
    val ingredientsOk: Int,
    val ingredientsNo: Int,
    val isHealthy: Boolean
)

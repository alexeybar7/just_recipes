package com.alexit.justrecipes.domain.model

data class RecipeCardModel(
    val id: Int,
    val name: String,
    val portion: Int?,
    val image: String?,
    val duration: Int?,
    val isHealthy: Boolean,
    val ingredients: List<Int>
)

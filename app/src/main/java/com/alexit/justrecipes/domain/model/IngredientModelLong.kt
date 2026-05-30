package com.alexit.justrecipes.domain.model

data class IngredientModelLong(
    val id: Int,
    val name: String,
    val energy: Double,
    val protein: Double,
    val fat: Double,
    val carbohydrate: Double,
    val quantity: Float?,
    val unit: String?,
    val density: Float?
)

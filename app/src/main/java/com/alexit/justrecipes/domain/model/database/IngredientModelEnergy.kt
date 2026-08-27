package com.alexit.justrecipes.domain.model.database

data class IngredientModelEnergy(
    val id: Int,
    val energy: Double,
    val protein: Double,
    val fat: Double,
    val carbohydrate: Double,
    val quantity: Double,
    val density: Double,
)

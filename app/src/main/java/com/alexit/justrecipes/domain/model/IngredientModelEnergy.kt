package com.alexit.justrecipes.domain.model

import androidx.room.ColumnInfo

data class IngredientModelEnergy(
    val id: Int,
    val energy: Double,
    val protein: Double,
    val fat: Double,
    val carbohydrate: Double,
    val quantity: Float?,
    val density: Float?,
)

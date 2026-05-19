package com.alexit.justrecipes.domain.model

import kotlin.time.Duration

data class RecipeModel(
    val id: Int,
    val name: String,
    val portion: Int?,
    val image: String?,
    val details: List<String>?,
    val detailsImage: List<String>?,
    val duration: Int?,
    val energy: Double,
    val fat: Double,
    val carbohydrate: Double,
    val protein: Double,
    val ingredients: List<IngredientModel>?
)

package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeAi(
    val name: String,
    val persons: Int,
    @SerialName("cooking_time") val cookingTime: Int,
    val ingredients: List<IngredientsAi>,
    val steps: List<String>
)
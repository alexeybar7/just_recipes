package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.Serializable

@Serializable
data class IngredientsAi(
    val name: String,
    val quantity: Int
)

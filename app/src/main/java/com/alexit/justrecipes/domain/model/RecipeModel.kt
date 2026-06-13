package com.alexit.justrecipes.domain.model

data class RecipeModel(
    val id: Int,
    val name: String,
    val duration: Int?,
    val portion: Int?,
    val image: String?,
    val ingredientsOk: Int,
    val ingredientsNo: Int
)

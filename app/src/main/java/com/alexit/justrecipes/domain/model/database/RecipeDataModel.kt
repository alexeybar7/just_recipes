package com.alexit.justrecipes.domain.model.database

data class RecipeDataModel(
    val id: Int,
    val name: String,
    val image: String,
    val portion: Int,
    val duration: Int,
    val details: String,
    val detailsImage: String,
)

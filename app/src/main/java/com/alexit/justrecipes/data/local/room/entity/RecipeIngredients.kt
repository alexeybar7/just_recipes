package com.alexit.justrecipes.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "recipe_ingredients",
    primaryKeys = ["recipeId", "ingredientId"],
)
data class RecipeIngredients(
    @ColumnInfo(name = "recipe_id") val recipeId: Int,
    @ColumnInfo(name = "ingredient_id")val ingredientId: Int,
    val quantity: Float?,
    val unit: String?,
    val density: Float?
)
package com.alexit.justrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_ingredients_own",
    primaryKeys = ["recipeOwnId", "ingredientOwnId"]
)
data class RecipeIngredientsOwn(
    @ColumnInfo(name = "recipe_own_id")val recipeOwnId: Int,
    @ColumnInfo(name = "ingredient_own_id")val ingredientOwnId: Int,
    val quantity: Float,
    val unit: String
)

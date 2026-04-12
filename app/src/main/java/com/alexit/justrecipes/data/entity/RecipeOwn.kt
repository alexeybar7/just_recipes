package com.alexit.justrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_own")
data class RecipeOwn(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "recipe_own_id") val recipeOwnId: Int,
    val name: String,
    val recipe: String?,
    val images: String?
)

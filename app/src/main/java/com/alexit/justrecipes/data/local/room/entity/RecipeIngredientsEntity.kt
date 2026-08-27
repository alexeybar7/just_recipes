package com.alexit.justrecipes.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "recipe_ingredients",
    primaryKeys = ["recipe_id", "ingredient_id"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("recipe_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = IngredientEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf(("ingredient_id")),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(value = ["ingredient_id"])
    ]
)
data class RecipeIngredientsEntity(
    @ColumnInfo(name = "recipe_id") val recipeId: Int,
    @ColumnInfo(name = "ingredient_id") val ingredientId: Int,
    val quantity: Double,
    val unit: String?,
    val density: Double
)
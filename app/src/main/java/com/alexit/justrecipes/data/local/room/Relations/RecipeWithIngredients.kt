package com.alexit.justrecipes.data.local.room.Relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.data.local.room.entity.RecipeEntity
import com.alexit.justrecipes.data.local.room.entity.RecipeIngredientsEntity

data class RecipeWithIngredients(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            RecipeIngredientsEntity::class,
            parentColumn = "recipe_id",
            entityColumn = "ingredient_id")
    )
    val ingredients: List<IngredientEntity>
)

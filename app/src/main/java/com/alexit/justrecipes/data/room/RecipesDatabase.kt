package com.alexit.justrecipes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexit.justrecipes.data.room.entity.Ingredient
import com.alexit.justrecipes.data.room.entity.Recipe
import com.alexit.justrecipes.data.room.entity.RecipeIngredients

@Database(
    version = 1,
    entities = [
        Recipe::class,
        Ingredient::class,
        RecipeIngredients::class,
    ]
)
abstract class RecipesDatabase : RoomDatabase()  {
    abstract fun recipesDao() : RecipesDao
}
package com.alexit.justrecipes.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexit.justrecipes.data.local.room.dao.RecipesDao
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.data.local.room.entity.RecipeEntity
import com.alexit.justrecipes.data.local.room.entity.RecipeIngredientsEntity

@Database(
    entities = [
        IngredientEntity::class,
        RecipeEntity::class,
        RecipeIngredientsEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class RecipesDatabase : RoomDatabase()  {
    abstract fun recipesDao() : RecipesDao
}
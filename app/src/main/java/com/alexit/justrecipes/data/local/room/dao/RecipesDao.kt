package com.alexit.justrecipes.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.alexit.justrecipes.domain.model.IngredientModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Query("SELECT id, name, category FROM ingredients")
    fun getListIngredients(): Flow<List<IngredientModel>>

    @Query("INSERT INTO ingredients (name, category)" +
            "VALUES (:ingredientName, :ingredientCategory)")
    suspend fun insertOwnIngredient(ingredientName: String, ingredientCategory: String)

    @Query("SELECT id, name, category, weight FROM ingredients WHERE is_inputted = 1")
    fun getListInputtedIngredients(): Flow<List<IngredientModel>>

    @Query("UPDATE ingredients SET is_inputted = 1 WHERE id = :ingredientId")
    suspend fun insertInputtedIngredient(ingredientId: Int)

    @Query("UPDATE ingredients SET weight = NULL, is_inputted = 0 WHERE id = :ingredientId")
    suspend fun deleteInputtedIngredient(ingredientId: Int)

    @Query("UPDATE ingredients SET weight = :ingredientWeight WHERE id = :ingredientId")
    suspend fun updateWeightIngredient(ingredientId: Int, ingredientWeight: Int)
}
package com.alexit.justrecipes.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.model.IngredientModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Query("SELECT id, name, category, weight, is_inputted FROM ingredients")
    fun getListIngredients(): Flow<List<IngredientModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwnIngredient(ingredient: IngredientEntity)

    @Query("UPDATE ingredients SET is_inputted = 1 WHERE id = :ingredientId")
    suspend fun insertInputtedIngredient(ingredientId: Int)

    @Query("UPDATE ingredients SET weight = NULL, is_inputted = 0 WHERE id = :ingredientId")
    suspend fun deleteInputtedIngredient(ingredientId: Int)

    @Query("UPDATE ingredients SET weight = :ingredientWeight WHERE id = :ingredientId")
    suspend fun updateWeightIngredient(ingredientId: Int, ingredientWeight: Int)

    @Query("SELECT AVG(DISTINCT energy) FROM ingredients WHERE category = :category")
    fun getAVGEnergy(category: String): Double

    @Query("SELECT AVG(DISTINCT protein)FROM ingredients WHERE category = :category")
    fun getAVGProtein(category: String): Double

    @Query("SELECT AVG(DISTINCT fat)FROM ingredients WHERE category = :category")
    fun getAVGFat(category: String): Double

    @Query("SELECT AVG(DISTINCT carbohydrate)FROM ingredients WHERE category = :category")
    fun getAVGCarbohydrate(category: String): Double
}

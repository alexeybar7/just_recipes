package com.alexit.justrecipes.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alexit.justrecipes.data.local.room.Relations.RecipeWithIngredients
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.model.ShortIngredientModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Query("SELECT id, name, is_inputted FROM ingredients WHERE name = :ingredientName")
    suspend fun getIngredient(ingredientName: String): ShortIngredientModel?

    @Query("SELECT name FROM ingredients")
    fun getIngredientsName(): Flow<List<String>>

    @Query("SELECT id, name, category, weight FROM ingredients WHERE is_inputted = 1")
    fun getInputtedIngredients(): Flow<List<IngredientModel>>

    @Query("SELECT DISTINCT category FROM ingredients ORDER BY category")
    suspend fun getCategories(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwnIngredient(ingredient: IngredientEntity)

    @Query("UPDATE ingredients SET is_inputted = 1 WHERE id = :ingredientId")
    suspend fun insertInputtedIngredient(ingredientId: Int)

    @Query("UPDATE ingredients SET weight = NULL, is_inputted = 0 WHERE id = :ingredientId")
    suspend fun deleteInputtedIngredient(ingredientId: Int)

    @Query("UPDATE ingredients SET weight = :ingredientWeight WHERE id = :ingredientId")
    suspend fun updateWeightIngredient(ingredientId: Int, ingredientWeight: Int)

    @Query("SELECT AVG(DISTINCT energy) FROM ingredients WHERE category = :category")
    suspend fun getAVGEnergy(category: String): Double

    @Query("SELECT AVG(DISTINCT protein)FROM ingredients WHERE category = :category")
    suspend fun getAVGProtein(category: String): Double

    @Query("SELECT AVG(DISTINCT fat)FROM ingredients WHERE category = :category")
    suspend fun getAVGFat(category: String): Double

    @Query("SELECT AVG(DISTINCT carbohydrate)FROM ingredients WHERE category = :category")
    suspend fun getAVGCarbohydrate(category: String): Double

    @Query("SELECT MAX(id) FROM ingredients")
    suspend fun getMAXIdIngredients(): Int

    @Transaction
    @Query("SELECT * From recipes")
    fun getRecipesWithIngredients(): List<RecipeWithIngredients>
}

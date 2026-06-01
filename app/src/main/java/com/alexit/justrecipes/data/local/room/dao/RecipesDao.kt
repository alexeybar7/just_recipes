package com.alexit.justrecipes.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alexit.justrecipes.data.local.room.Relations.RecipeWithIngredients
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.model.IngredientModelEnergy
import com.alexit.justrecipes.domain.model.IngredientModelShort
import com.alexit.justrecipes.domain.model.RecipeModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Query("SELECT id, name, is_inputted FROM ingredients WHERE name = :ingredientName")
    suspend fun getIngredient(ingredientName: String): IngredientModelShort?

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
    @Query("SELECT * FROM recipes")
    fun getRecipesWithIngredients(): Flow<List<RecipeWithIngredients>>

    @Query("SELECT " +
            "recipes.id, recipes.name, recipes.duration, recipes.portion, recipes.image, " +
            "ingredients.id, ingredients.energy, ingredients.protein, ingredients.fat, ingredients.carbohydrate, " +
            "recipe_ingredients.quantity, recipe_ingredients.density " +
            "FROM recipe_ingredients " +
            "INNER JOIN recipes ON recipes.id = recipe_ingredients.recipe_id " +
            "INNER JOIN ingredients ON ingredients.id = recipe_ingredients.ingredient_id" )
    fun getRecipesCardData(): Flow<Map<RecipeModel, List<IngredientModelEnergy>>>

    @Query("SELECT id FROM ingredients WHERE is_inputted = 1")
    fun getInputtedIngredientsId(): Flow<List<Int>>
}
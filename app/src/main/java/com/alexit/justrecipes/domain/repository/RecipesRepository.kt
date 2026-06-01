package com.alexit.justrecipes.domain.repository

import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.data.local.room.Relations.RecipeWithIngredients
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.model.IngredientModelEnergy
import com.alexit.justrecipes.domain.model.IngredientModelShort
import com.alexit.justrecipes.domain.model.RecipeModel
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun getIngredient(ingredientName: String): IngredientModelShort?
    fun getIngredientsName(): Flow<SourceState<List<String>>>
    fun getInputtedIngredients(): Flow<SourceState<List<IngredientModel>>>
    suspend fun getCategories(): List<String>
    suspend fun addNewIngredient(ingredient: IngredientEntity)
    suspend fun addInputtedIngredient(ingredientId: Int)
    suspend fun removeInputtedIngredient(ingredientId: Int)
    suspend fun changeWeightIngredient(ingredientId: Int, ingredientWeight: Int)
    suspend fun getAVGEnergy(category: String): Double
    suspend fun getAVGProtein(category: String): Double
    suspend fun getAVGFat(category: String): Double
    suspend fun getAVGCarbohydrate(category: String): Double
    suspend fun getMAXIdIngredients(): Int
    fun getRecipesWithIngredients(): Flow<List<RecipeWithIngredients>>
    fun getRecipesCardData(): Flow<Map<RecipeModel, List<IngredientModelEnergy>>>
    fun getInputtedIngredientsId(): Flow<List<Int>>
}
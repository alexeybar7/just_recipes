package com.alexit.justrecipes.domain.repository

import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.presentation.feature.ResourcesState
import com.alexit.justrecipes.domain.model.IngredientModel
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getIngredients(): Flow<List<IngredientModel>>
    suspend fun addNewIngredient(ingredient: IngredientEntity)
    suspend fun addInputtedIngredient(ingredientId: Int)
    suspend fun removeInputtedIngredient(ingredientId: Int)
    suspend fun changeWeightIngredient(ingredientId: Int, ingredientWeight: Int)
    fun getAVGParameter(parameter: String, category: String): Float
}
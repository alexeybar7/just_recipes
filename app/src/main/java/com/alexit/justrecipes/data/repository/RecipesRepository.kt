package com.alexit.justrecipes.data.repository

import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.data.local.room.dao.RecipesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RecipesRepository {

    fun getIngredients(): Flow<ResourcesState<List<IngredientModel>>>
    suspend fun addNewIngredient(ingredientId: Int, ingredientName: String, ingredientCategory: String)
    fun getInputtedIngredients(): Flow<ResourcesState<List<IngredientModel>>>
    suspend fun addInputtedIngredient(ingredientId: Int)
    suspend fun removeInputtedIngredient(ingredientId: Int)
    suspend fun changeWeightIngredient(ingredientId: Int, ingredientWeight: Int)
}

class RecipesRepositoryImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : RecipesRepository {

    override fun getIngredients(): Flow<ResourcesState<List<IngredientModel>>> = flow {
        emit(ResourcesState.Loading)
        recipesDao.getListIngredients().collect { ingredients ->
            emit(ResourcesState.Success(ingredients))
        }
    }.catch { e ->
        emit(ResourcesState.Error(e.localizedMessage ?: "Unknown error occurred"))
    }

    override suspend fun addNewIngredient(ingredientId: Int,ingredientName: String, ingredientCategory: String) {
        try {
            recipesDao.insertOwnIngredient(ingredientId, ingredientName, ingredientCategory)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getInputtedIngredients(): Flow<ResourcesState<List<IngredientModel>>> = flow {
        emit(ResourcesState.Loading)
        recipesDao.getListInputtedIngredients().collect { ingredients ->
            emit(ResourcesState.Success(ingredients))
        }
    }.catch { e ->
        emit(ResourcesState.Error(e.localizedMessage ?: "Unknown error occurred"))
    }

    override suspend fun addInputtedIngredient(ingredientId: Int) {
        try {
            recipesDao.insertInputtedIngredient(ingredientId)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun removeInputtedIngredient(ingredientId: Int) {
        try {
            recipesDao.deleteInputtedIngredient(ingredientId)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun changeWeightIngredient(ingredientId: Int, ingredientWeight: Int) {
        try {
            recipesDao.updateWeightIngredient(ingredientId, ingredientWeight)
        } catch (e: Exception) {
            throw e
        }
    }
}

package com.alexit.justrecipes.data.repository

import com.alexit.justrecipes.data.model.IngredientModel
import com.alexit.justrecipes.data.room.RecipesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RecipesRepository {

    fun getIngredients(): Flow<List<InputtedIngredients>>
    suspend fun addIngredient(ingredient: OwnIngredient)
    fun getInputtedIngredients(): Flow<List<InputtedIngredients>>
    suspend fun addInputtedIngredient(ingredient: InputtedIngredients)
    suspend fun removeInputtedIngredient(ingredient: InputtedIngredients)
    suspend fun changeWeightIngredient(id: Int, weight: Int?)
}

class RecipesRepositoryImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : RecipesRepository {

    override fun getIngredients(): Flow<List<IngredientModel>> {
        return recipesDao.getListIngredients()
    }

    override suspend fun addIngredient(ingredient: OwnIngredient) {
        recipesDao.insertOwnIngredient(ingredient)
    }

    override fun getInputtedIngredients(): Flow<List<InputtedIngredients>> {
        return recipesDao.getListInputtedIngredients()
    }

    override suspend fun addInputtedIngredient(ingredient: InputtedIngredients) {
        recipesDao.insertInputtedIngredient(ingredient)
    }

    override suspend fun removeInputtedIngredient(ingredient: InputtedIngredients) {
        recipesDao.deleteInputtedIngredient(ingredient)
    }

    override suspend fun changeWeightIngredient(id: Int, weight: Int?) {
        recipesDao.updateWeightIngredient(id, weight)
    }
}
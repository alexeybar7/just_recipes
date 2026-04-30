package com.alexit.justrecipes.data.repository

import com.alexit.justrecipes.data.local.room.dao.RecipesDao
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : RecipesRepository {

    override suspend fun getIngredient(ingredientName: String): IngredientModel? {
        return recipesDao.getIngredient(ingredientName)
    }

    override fun getSuggestions(): Flow<List<String>> {
        return recipesDao.getSuggestions()
    }

    override fun getInputtedIngredients(): Flow<List<IngredientModel>> {
        return recipesDao.getInputtedIngredients()
    }

    override suspend fun getCategories(): List<String> {
        return recipesDao.getCategories()
    }

    override suspend fun addNewIngredient(ingredient: IngredientEntity) {
        recipesDao.insertOwnIngredient(ingredient)
    }

    override suspend fun addInputtedIngredient(ingredientId: Int) {
        recipesDao.insertInputtedIngredient(ingredientId)
    }

    override suspend fun removeInputtedIngredient(ingredientId: Int) {
        recipesDao.deleteInputtedIngredient(ingredientId)
    }

    override suspend fun changeWeightIngredient(ingredientId: Int, ingredientWeight: Int) {
        recipesDao.updateWeightIngredient(ingredientId, ingredientWeight)
    }

    override suspend fun getAVGEnergy(category: String): Double {
        return recipesDao.getAVGEnergy(category)
    }

    override suspend fun getAVGProtein(category: String): Double {
        return recipesDao.getAVGProtein(category)
    }

    override suspend fun getAVGFat(category: String): Double {
        return recipesDao.getAVGFat(category)
    }

    override suspend fun getAVGCarbohydrate(category: String): Double {
        return recipesDao.getAVGCarbohydrate(category)
    }

    override suspend fun getMAXIdIngredients(): Int {
        return recipesDao.getMAXIdIngredients()
    }
}

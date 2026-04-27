package com.alexit.justrecipes.data.repository

import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.data.local.room.dao.RecipesDao
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : RecipesRepository {

    override fun getIngredients(): Flow<List<IngredientModel>> {
        return recipesDao.getListIngredients()
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

    override fun getAVGEnergy(category: String): Double {
        return recipesDao.getAVGEnergy(category)
    }

    override fun getAVGProtein(category: String): Double {
        return recipesDao.getAVGProtein(category)
    }

    override fun getAVGFat(category: String): Double {
        return recipesDao.getAVGFat(category)
    }

    override fun getAVGCarbohydrate(category: String): Double {
        return recipesDao.getAVGCarbohydrate(category)
    }
}

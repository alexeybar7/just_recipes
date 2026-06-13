package com.alexit.justrecipes.data.repository

import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.common.asSourceState
import com.alexit.justrecipes.data.local.room.Relations.RecipeWithIngredients
import com.alexit.justrecipes.data.local.room.dao.RecipesDao
import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.model.IngredientModelEnergy
import com.alexit.justrecipes.domain.model.IngredientModelShort
import com.alexit.justrecipes.domain.model.RecipeModel
import com.alexit.justrecipes.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : RecipesRepository {

    override suspend fun getIngredient(ingredientName: String): IngredientModelShort? {
        return recipesDao.getIngredient(ingredientName)
    }

    override fun getIngredientsName(): Flow<SourceState<List<String>>> {
        return recipesDao.getIngredientsName().asSourceState()
    }

    override fun getInputtedIngredients(): Flow<SourceState<List<IngredientModel>>> {
        return recipesDao.getInputtedIngredients().asSourceState()
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

    override fun getRecipesWithIngredients(): Flow<List<RecipeWithIngredients>> {
        return recipesDao.getRecipesWithIngredients()
    }

    override fun getRecipesCardData(query: String): Flow<Map<RecipeModel, List<IngredientModelEnergy>>> {
        val formattedQuery = "%$query%"
        return recipesDao.getRecipesCardData(formattedQuery)
    }

    override fun getInputtedIngredientsId(): Flow<List<Int>> {
        return recipesDao.getInputtedIngredientsId()
    }
}

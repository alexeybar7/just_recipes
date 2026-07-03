package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.domain.model.HealthyFoodModel
import com.alexit.justrecipes.domain.model.IngredientDataModel
import com.alexit.justrecipes.domain.model.IngredientModelEnergy
import com.alexit.justrecipes.domain.model.IngredientModelFull
import com.alexit.justrecipes.domain.model.RecipeDataModel
import com.alexit.justrecipes.domain.model.RecipeModelFull
import com.alexit.justrecipes.domain.repository.RecipesRepository
import com.alexit.justrecipes.utility.getHealthyFoodData
import javax.inject.Inject
import kotlin.Int

class GetRecipeFullDataUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(recipeId: Int): RecipeModelFull {
        val recipeData: RecipeDataModel = recipesRepository.getRecipeData(recipeId)
        val ingredientsFullData: List<IngredientModelFull> = recipesRepository.getIngredientsData(recipeId)
        val healthyFoodData: HealthyFoodModel = getHealthyFoodData(ingredientsFullData.map {
            IngredientModelEnergy(
                id = it.id,
                energy = it.energy,
                protein = it.protein,
                fat = it.fat,
                carbohydrate = it.carbohydrate,
                quantity = it.quantity,
                density = it.density,
            )
        })
        val recipe = RecipeModelFull (
            id = recipeData.id,
            name = recipeData.name,
            image = recipeData.image,
            portion = recipeData.portion,
            duration = recipeData.duration,
            energy = healthyFoodData.energy,
            protein = healthyFoodData.protein,
            fat = healthyFoodData.fat,
            carbohydrate = healthyFoodData.carbohydrate,
            details = recipeData.details,
            detailsImage = recipeData.detailsImage,
            ingredients = ingredientsFullData.map {
                IngredientDataModel(
                    id = it.id,
                    name = it.name,
                    quantity = it.quantity,
                    unit = it.unit,
                    density = it.density,
                    weight = it.weight,
                    isInputted = it.isInputted
                )
            }
        )
        return recipe
    }
}
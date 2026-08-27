package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.data.local.room.entity.RecipeEntity
import com.alexit.justrecipes.data.local.room.entity.RecipeIngredientsEntity
import com.alexit.justrecipes.domain.model.ai.RecipeAi
import com.alexit.justrecipes.domain.repository.RecipesRepository
import javax.inject.Inject

const val UNIT_AI = "г"
const val DENSITY_AI = 1.0
const val IMAGE_AI = "ai"

class AddAiRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(recipeAi: RecipeAi) {
        val recipeId = recipesRepository.getMAXIdRecipes() + 1
        val details = recipeAi.steps.joinToString("(^_^)")
        val recipe = RecipeEntity(
            id = recipeId,
            name = recipeAi.name,
            portion = recipeAi.persons,
            image = IMAGE_AI,
            details = details,
            detailsImg = null,
            duration = recipeAi.cookingTime
        )
        recipeAi.ingredients.forEach { ingredient ->
            if (!recipesRepository.checkExistIngredient(ingredient.name)) {
                recipesRepository.addNewIngredient(
                    IngredientEntity(
                        id = recipesRepository.getMAXIdIngredients() + 1,
                        name = ingredient.name,
                        energy = -1.0,
                        protein = -1.0,
                        fat = -1.0,
                        carbohydrate = -1.0,
                        synonym = null,
                        category = "-",
                        weight = null,
                        isInputted = false,
                        isSynonym = 0
                    )
                )
            }
        }
        val recipeIngredients = recipeAi.ingredients.map { ingredient ->
            val ingredientId = recipesRepository.getIngredientId(ingredient.name)
            RecipeIngredientsEntity(
                recipeId = recipeId,
                ingredientId = ingredientId,
                quantity = ingredient.quantity,
                unit = UNIT_AI,
                density = DENSITY_AI
            )
        }
        recipesRepository.addNewRecipe(recipe, recipeIngredients)
    }
}
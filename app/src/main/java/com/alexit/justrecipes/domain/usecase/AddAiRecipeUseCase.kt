package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.data.local.room.entity.RecipeEntity
import com.alexit.justrecipes.data.local.room.entity.RecipeIngredientsEntity
import com.alexit.justrecipes.domain.model.ai.RecipeAi
import com.alexit.justrecipes.domain.model.database.IngredientIdNameModel
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

        val listIngredients: List<IngredientIdNameModel> = recipesRepository.getIngredientIdName()
        val recipeIngredients = recipeAi.ingredients.map { ingredient ->
            var ingredientId = getIngredientId(
                ingredient.name.replaceFirstChar { it.lowercase() }.replace('ё', 'е'),
                listIngredients
            )
            if (ingredientId == -1) {
                ingredientId = recipesRepository.getMAXIdIngredients() + 1
                recipesRepository.addNewIngredient(
                    IngredientEntity(
                        id = ingredientId,
                        name = ingredient.name.replaceFirstChar { it.lowercase() },
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

private fun getIngredientId(name: String, listIngredients: List<IngredientIdNameModel>): Int {
    val nameCleaned = name.replace(Regex("\\([^)]*\\)"), "").trim()
    val ingredient: IngredientIdNameModel? = listIngredients.find { it.name == nameCleaned }
    if (ingredient != null) return ingredient.id
    else {
        listIngredients.forEach {
            val wordsIngredient = it.name.split(" ").toSet()
            val wordsName = name.split(" ").toSet()
            if (wordsIngredient == wordsName)  return it.id
        }
    }
    return -1
}
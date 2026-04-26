package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.repository.RecipesRepository
import javax.inject.Inject

class AddNewIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int, ingredientName: String, ingredientCategory: String) {
        val energy = recipesRepository.getAVGParameter("energy", ingredientCategory)
        val protein = recipesRepository.getAVGParameter("protein", ingredientCategory)
        val fat = recipesRepository.getAVGParameter("fat", ingredientCategory)
        val carbohydrate = recipesRepository.getAVGParameter("carbohydrate", ingredientCategory)
        recipesRepository.addNewIngredient(
            IngredientEntity(
                id = ingredientId,
                name = ingredientName,
                energy = energy,
                protein = protein,
                fat = fat,
                carbohydrate = carbohydrate,
                category = ingredientCategory,
                isInputted = true
            )
        )
        recipesRepository.addInputtedIngredient(ingredientId)
    }
}




package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.data.repository.RecipesRepository
import javax.inject.Inject

class AddIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int,ingredientName: String, ingredientCategory: String) {
        recipesRepository.addNewIngredient(ingredientId,ingredientName, ingredientCategory)
    }
}
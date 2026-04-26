package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.domain.repository.RecipesRepository
import javax.inject.Inject

class ChangeWeightIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int, ingredientWeight: Int) {
        recipesRepository.changeWeightIngredient(ingredientId, ingredientWeight)
    }
}
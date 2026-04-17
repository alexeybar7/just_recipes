package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.data.repository.RecipesRepository
import javax.inject.Inject

class RemoveInputtedIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int) {
        recipesRepository.removeInputtedIngredient(ingredientId)
    }
}
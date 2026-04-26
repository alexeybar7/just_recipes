package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.domain.repository.RecipesRepository
import javax.inject.Inject

class AddInputtedIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int) {
        recipesRepository.addInputtedIngredient(ingredientId)
    }
}
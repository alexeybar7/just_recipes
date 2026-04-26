package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.repository.RecipesRepository
import javax.inject.Inject

class RemoveInputtedIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int) {
        recipesRepository.removeInputtedIngredient(ingredientId)
    }
}
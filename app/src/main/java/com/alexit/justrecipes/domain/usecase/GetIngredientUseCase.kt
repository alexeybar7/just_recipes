package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.domain.model.database.IngredientModelShort
import com.alexit.justrecipes.domain.repository.RecipesRepository
import javax.inject.Inject

class GetIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientName: String): IngredientModelShort? {
        return recipesRepository.getIngredient(ingredientName)
    }
}
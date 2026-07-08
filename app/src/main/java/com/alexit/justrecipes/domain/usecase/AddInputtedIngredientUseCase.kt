package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.domain.repository.RecipesRepository
import javax.inject.Inject

class AddInputtedIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int, synonym: String?) {
        val synonymOk = synonym?:""
        recipesRepository.addInputtedIngredient(ingredientId, synonymOk)
    }
}
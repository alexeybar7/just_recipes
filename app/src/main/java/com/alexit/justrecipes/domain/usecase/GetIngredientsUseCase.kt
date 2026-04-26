package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIngredientsUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    operator fun invoke(): Flow<List<IngredientModel>> =
         recipesRepository.getIngredients()
}
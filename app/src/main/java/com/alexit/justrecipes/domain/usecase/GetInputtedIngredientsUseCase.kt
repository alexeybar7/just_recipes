package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.data.repository.RecipesRepository
import com.alexit.justrecipes.data.repository.ResourcesState
import com.alexit.justrecipes.domain.model.IngredientModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInputtedIngredientsUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    operator fun invoke(): Flow<ResourcesState<List<IngredientModel>>> {
        return recipesRepository.getInputtedIngredients()
    }
}
package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.domain.model.CategoryModel
import com.alexit.justrecipes.domain.repository.RecipesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(): List<CategoryModel> {
        val categories = recipesRepository.getCategories()
        return categories.mapIndexed { index, category -> CategoryModel(id = index, category) }
    }
}
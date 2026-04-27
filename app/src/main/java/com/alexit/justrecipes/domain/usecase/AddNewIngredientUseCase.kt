package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.data.local.room.entity.IngredientEntity
import com.alexit.justrecipes.domain.repository.RecipesRepository
import java.math.RoundingMode
import javax.inject.Inject

class AddNewIngredientUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend operator fun invoke(ingredientId: Int, ingredientName: String, ingredientCategory: String) {
        val energy = recipesRepository.getAVGEnergy(ingredientCategory)
        val protein = recipesRepository.getAVGProtein(ingredientCategory)
        val fat = recipesRepository.getAVGFat(ingredientCategory)
        val carbohydrate = recipesRepository.getAVGCarbohydrate(ingredientCategory)
        recipesRepository.addNewIngredient(
            IngredientEntity(
                id = ingredientId,
                name = ingredientName,
                energy = energy.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble(),
                protein = protein.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble(),
                fat = fat.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble(),
                carbohydrate = carbohydrate.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble(),
                category = ingredientCategory,
                isInputted = true
            )
        )
    }
}

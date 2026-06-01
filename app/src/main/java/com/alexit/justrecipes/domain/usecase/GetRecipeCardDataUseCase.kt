package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.common.asSourceState
import com.alexit.justrecipes.domain.model.IngredientModelEnergy
import com.alexit.justrecipes.domain.model.RecipeCardModel
import com.alexit.justrecipes.domain.repository.RecipesRepository
import com.alexit.justrecipes.utility.LIMIT_ENERGY
import com.alexit.justrecipes.utility.RATIO_CARBO_FAT_PROTEIN
import com.alexit.justrecipes.utility.getHealthyFoodData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecipeCardDataUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) { operator fun invoke(): Flow<SourceState<List<RecipeCardModel>>> {
        val flow: Flow<List<RecipeCardModel>> =
            recipesRepository.getRecipesCardData().map { value ->
                value.map {
                    val recipeIngredientsId: List<Int> = it.value.map { ingredient -> ingredient.id }
                    RecipeCardModel(
                        id = it.key.id,
                        name = it.key.name,
                        image = it.key.image,
                        portion = it.key.portion,
                        isHealthy = isHealthy(it.value, it.key.portion),
                        duration = it.key.duration,
                        ingredients = recipeIngredientsId
                    )
                }
            }
        return flow.asSourceState()
    }
}

fun isHealthy(ingredients: List<IngredientModelEnergy>, portion: Int?): Boolean {
    val healthyFoodData = getHealthyFoodData(ingredients)
    val portionOk: Int = portion ?: 1
    val isHealthy =
        healthyFoodData.carbohydrate / healthyFoodData.fat > RATIO_CARBO_FAT_PROTEIN &&
                healthyFoodData.carbohydrate / healthyFoodData.protein > RATIO_CARBO_FAT_PROTEIN &&
                healthyFoodData.energy <= LIMIT_ENERGY * portionOk
    return isHealthy
}


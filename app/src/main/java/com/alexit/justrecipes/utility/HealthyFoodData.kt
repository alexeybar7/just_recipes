package com.alexit.justrecipes.utility

import com.alexit.justrecipes.domain.model.HealthyFoodModel
import com.alexit.justrecipes.domain.model.IngredientModelLong

const val LIMIT_ENERGY = 600
const val RATIO_CARBO_FAT_PROTEIN = 3.5

fun getHealthyFoodData(ingredients: List<IngredientModelLong>): HealthyFoodModel {
    val healthyFoodModel = HealthyFoodModel(0.0, 0.0, 0.0, 0.0)
    val ingredientTotal = ingredients.fold(healthyFoodModel) { acc, entity ->
        val amount = if (entity.quantity == null || entity.density == null) 1.0
        else {
            entity.quantity * entity.density
        }.toDouble()
        HealthyFoodModel(
            energy = acc.energy + (entity.energy * amount),
            protein = acc.protein + (entity.protein * amount),
            fat = acc.fat + (entity.fat * amount),
            carbohydrate = acc.carbohydrate + (entity.carbohydrate * amount),

            )
    }
    return ingredientTotal
}
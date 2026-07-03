package com.alexit.justrecipes.utility

import com.alexit.justrecipes.domain.model.HealthyFoodModel
import com.alexit.justrecipes.domain.model.IngredientModelEnergy

fun getHealthyFoodData(ingredients: List<IngredientModelEnergy>): HealthyFoodModel {
    val healthyFoodModel = HealthyFoodModel(0.0, 0.0, 0.0, 0.0)
    val ingredientTotal = ingredients.fold(healthyFoodModel) { acc, entity ->
        val amount = if (entity.quantity == null || entity.density == null) 1.0
        else {
            (entity.quantity * entity.density) / 100
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
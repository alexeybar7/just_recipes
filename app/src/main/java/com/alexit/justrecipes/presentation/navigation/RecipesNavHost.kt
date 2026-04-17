package com.alexit.justrecipes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alexit.justrecipes.presentation.feature.inputingrediets.InputIngredientsScreen
import com.alexit.justrecipes.presentation.feature.ownrecipes.OwnRecipesScreen
import com.alexit.justrecipes.presentation.feature.requestai.RequestAiScreen
import com.alexit.justrecipes.presentation.feature.searchrecipe.SearchRecipeScreen

@Composable
fun RecipesNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = InputIngredients.route,
    ) {
        composable(route = InputIngredients.route) {
            InputIngredientsScreen()
        }
        composable(route = SearchRecipe.route) {
            SearchRecipeScreen()
        }
        composable(route = RequestAi.route) {
            RequestAiScreen()
        }
        composable(OwnRecipes.route) {
            OwnRecipesScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
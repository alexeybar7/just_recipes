package com.alexit.justrecipes.presentation.navigation

import com.alexit.justrecipes.R

sealed interface RecipesDestinations {
    val icon: Int
    val iconDescription: Int
    val route: String
}

data object InputIngredients : RecipesDestinations {
    override val icon = R.drawable.format_list_bulleted_24px
    override val iconDescription = R.string.button_input_ingredients
    override val route = "input_ingredients"
}

data object SearchRecipe : RecipesDestinations {
    override val icon = R.drawable.menu_book_24px
    override val iconDescription = R.string.button_search_recipe
    override val route = "search_recipe"
}

data object RequestAi : RecipesDestinations {
    override val icon = R.drawable.psychology_24px
    override val iconDescription = R.string.button_request_ai
    override val route = "request_ai"
}

data object OwnRecipes : RecipesDestinations {
    override val icon = R.drawable.note_stack_24px
    override val iconDescription = R.string.button_own_recipes
    override val route = "own_recipes"
}

val recipesBottomMenuScreens = listOf(InputIngredients, SearchRecipe, RequestAi, OwnRecipes)
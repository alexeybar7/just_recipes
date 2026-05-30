package com.alexit.justrecipes.presentation.feature.searchrecipes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.data.local.room.Relations.RecipeWithIngredients
import com.alexit.justrecipes.domain.model.RecipeCardModel
import com.alexit.justrecipes.presentation.components.CircleLoader
import com.alexit.justrecipes.presentation.components.CustomDivider
import com.alexit.justrecipes.presentation.components.CustomTextField
import com.alexit.justrecipes.presentation.components.TitlePanel
import com.alexit.justrecipes.presentation.components.dpToPx
import com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel.SearchRecipeIntent
import com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel.SearchRecipesViewModel
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme

@Composable
fun SearchRecipesScreen(
    searchRecipesViewModel: SearchRecipesViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    val recipesWithIngredients = searchRecipesViewModel.recipesWithIngredients.collectAsStateWithLifecycle()
    val recipeCardData = searchRecipesViewModel.recipeCardData.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()
    ) {
        TitlePanel(
            text = stringResource(R.string.title_search_recipe),
        )
        Column(
            modifier = Modifier
                .padding(vertical = JustRecipesTheme.dimensions.paddingFieldInput)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                state = searchRecipesViewModel.inputTextStateIngredient,
                onDoneClick = { recipeName: String ->
                    searchRecipesViewModel.handleIntent(
                        SearchRecipeIntent.SelectRecipe(recipeName)
                    )
                },
                iconPlaceholder = R.drawable.text_search,
                iconDescriptionPlaceholder = R.string.icon_text_search,
                placeholder = stringResource(R.string.placeholder_search_recipes),
            )

            when (val sourceState = recipeCardData.value) {
                is SourceState.Loading -> LoadingScreen()
                is SourceState.Success -> ShowListRecipes(
                    //recipesDataCard.value,
                    recipeCardData = sourceState.data,
                    onRecipeClick
                )
                is SourceState.Error -> ErrorScreen()
            }
        }
    }
}

@Composable
fun ShowListRecipes(recipeCardData: List<RecipeCardModel>, onRecipeClick: (Int) -> Unit) {
    val padding = JustRecipesTheme.dimensions.gap1
    val widthRecipeCard = JustRecipesTheme.dimensions.widthRecipeCard
    val heightRecipeCard = JustRecipesTheme.dimensions.heightRecipeCard
    val backgroundRecipeCard = JustRecipesTheme.colors.background2
    val roundedCorner = JustRecipesTheme.dimensions.radiusCornerField
    val borderColor = JustRecipesTheme.colors.border2
    val borderThickness = JustRecipesTheme.dimensions.borderThickness
    val widthNameRecipeCard = JustRecipesTheme.dimensions.widthNameRecipeCard
    val heightNameRecipeCard = JustRecipesTheme.dimensions.heightNameRecipeCard
    val textNameStyle = JustRecipesTheme.typography.text1
    val colorText = JustRecipesTheme.colors.text4
    val sizeImage = JustRecipesTheme.dimensions.sizeImageRecipeCard
    val sizeIcon = JustRecipesTheme.dimensions.sizeIcon4
    val textIconStyle = JustRecipesTheme.typography.text5
    val colorIconOk = JustRecipesTheme.colors.iconOk
    val widthIconInfo = JustRecipesTheme.dimensions.widthIconInfoRecipeCard

    val ingredientsComplete = false
    //val deltaIngredients = 4
    val recipeHealthyEating = true

    LazyColumn() {
        items(items = recipeCardData, key = { it.id }) { recipe ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .size(
                        width = widthRecipeCard, height = heightRecipeCard
                    )
                    .background(
                        color = backgroundRecipeCard, shape = RoundedCornerShape(
                            size = roundedCorner
                        )
                    )
                    .border(
                        border = BorderStroke(
                            width = borderThickness, color = borderColor
                        ), shape = RoundedCornerShape(
                            size = roundedCorner,
                        )
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = padding, end = padding)
                        .height(heightNameRecipeCard)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicText(
                        modifier = Modifier
                            .padding(start = padding)
                            .width(widthNameRecipeCard)
                            .clickable(
                                enabled = true, onClick = { onRecipeClick(recipe.id) }),
                        style = textNameStyle,
                        color = { colorText },
                        text = "${recipe.id}. ${recipe.name}"
                    )
                    Image(
                        modifier = Modifier
                            .padding(end = padding)
                            .size(sizeImage)
                            .clip(
                                RoundedCornerShape(
                                    roundedCorner
                                )
                            ),
                        painter = painterResource(R.drawable.logo),
                        contentDescription = recipe.name
                    )
                }
                CustomDivider(
                    color = borderColor,
                    thickness = borderThickness.dpToPx(),
                    startX = padding.dpToPx(),
                    endX = (widthRecipeCard - padding * 2).dpToPx(),
                    startY = 0f,
                    endY = 0f
                )
                Row(
                    modifier = Modifier
                        .padding(start = padding, end = padding)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .width(widthIconInfo),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            modifier = Modifier
                                .size(sizeIcon),
                            imageVector = ImageVector.vectorResource(id = R.drawable.nest_clock_farsight_analog_24px),
                            contentDescription = stringResource(id = R.string.icon_duration),
                            colorFilter = ColorFilter.tint(colorText)
                        )
                        BasicText(
                            style = textIconStyle,
                            color = { colorText },
                            text = "${recipe.duration} ${stringResource(R.string.minute)}"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .width(widthIconInfo),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            modifier = Modifier
                                .size(sizeIcon),
                            imageVector = ImageVector.vectorResource(id = R.drawable.group_24px),
                            contentDescription = stringResource(id = R.string.icon_portion),
                            colorFilter = ColorFilter.tint(colorText)
                        )
                        BasicText(
                            style = textIconStyle,
                            color = { colorText },
                            text = "${recipe.portion} ${stringResource(R.string.portion)}"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .width(widthIconInfo),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        if (ingredientsComplete) {
                            Image(
                                modifier = Modifier
                                    .size(sizeIcon),
                                imageVector = ImageVector.vectorResource(id = R.drawable.data_check_24px),
                                contentDescription = stringResource(id = R.string.icon_ingredient_data_ok),
                                colorFilter = ColorFilter.tint(colorIconOk)
                            )
                        } else {
                            Image(
                                modifier = Modifier
                                    .size(sizeIcon),
                                imageVector = ImageVector.vectorResource(id = R.drawable.data_info_alert_24px),
                                contentDescription = stringResource(id = R.string.icon_ingredient_data_not),
                                colorFilter = ColorFilter.tint(colorText)
                            )
                            BasicText(
                                style = textIconStyle,
                                color = { colorText },
                                text = "${recipe.numberIngredients} ${stringResource(R.string.ingredient)}"
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .width(widthIconInfo),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (recipe.isHealthy) {
                            Image(
                                modifier = Modifier
                                    .size(sizeIcon),
                                imageVector = ImageVector.vectorResource(id = R.drawable.nest_eco_leaf_24px),
                                contentDescription = stringResource(id = R.string.icon_healthy_eating),
                                colorFilter = ColorFilter.tint(colorIconOk)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircleLoader(
            color = JustRecipesTheme.colors.circleLoader,
            modifier = Modifier.size(JustRecipesTheme.dimensions.sizeCircleLoader),
            isVisible = isLoading
        )
    }
}

@Composable
fun ErrorScreen() {}

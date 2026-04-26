package com.alexit.justrecipes.presentation.feature.inputingrediets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexit.justrecipes.R
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.presentation.components.CustomDialog
import com.alexit.justrecipes.presentation.components.CustomPopup
import com.alexit.justrecipes.presentation.components.CustomTextField
import com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel.InputIngredientsIntent
import com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel.InputIngredientsViewModel
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import kotlinx.collections.immutable.toPersistentList

@Composable
fun InputIngredientsScreen(
   inputIngredientsViewModel: InputIngredientsViewModel = hiltViewModel()
) {
    val inputIngredientsUiState by inputIngredientsViewModel.uiState.collectAsStateWithLifecycle()
    val ingredients = inputIngredientsViewModel.ingredients.collectAsStateWithLifecycle()
    //val inputtedIngredients = ingredients.filter { it.isInputted }


    Column(
        modifier = Modifier.fillMaxSize()
        ) {
        InputIngredientsTitlePanel(
            height = JustRecipesTheme.dimensions.heightTitlePanel,
            background = JustRecipesTheme.colors.background1,
            color = JustRecipesTheme.colors.text1,
            padding = JustRecipesTheme.dimensions.paddingTextTitlePanel,
            text = stringResource(R.string.title_input_ingredients),
            style = JustRecipesTheme.typography.title1
        )



            BasicText(
                text = ingredients.value.size.toString(),
                style = JustRecipesTheme.typography.title1,
                //color = JustRecipesTheme.colors.onTitlePanel
            )

        /*
        Column(
            modifier = Modifier
                .padding(vertical = JustRecipesTheme.dimensions.paddingFieldInput)
                .background(JustRecipesTheme.colors.background0)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                state = inputIngredientsViewModel.inputTextStateIngredient,
                onDoneClick = { ingredientName: String ->
                    inputIngredientsViewModel.handleIntent(
                    InputIngredientsIntent.CheckingSelectedIngredient(ingredientName)
                    ) },
                height = JustRecipesTheme.dimensions.heightFieldInput,
                width = JustRecipesTheme.dimensions.widthInputtedIngredient,
                textStyle = JustRecipesTheme.typography.input1,
                placeholder = stringResource(R.string.placeholder_input_ingredients),
                focusedField = JustRecipesTheme.colors.background2,
                focusedBorderField = JustRecipesTheme.colors.border2,
                focusedTextColor = JustRecipesTheme.colors.text2,
                unfocusedField = JustRecipesTheme.colors.background3,
                unfocusedBorderField = JustRecipesTheme.colors.border3,
                unfocusedTextColor = JustRecipesTheme.colors.text3,
                contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                borderThickness = JustRecipesTheme.dimensions.borderThickness,
                sizeIcon = JustRecipesTheme.dimensions.sizeIcon1,
                colorIcon = JustRecipesTheme.colors.iconSearchIngredient
            )

            if (inputIngredientsViewModel.inputTextStateIngredient.text.isNotEmpty()){
                SuggestionsIngredientsShow(
                    state = inputIngredientsViewModel.inputTextStateIngredient,
                    ingredientsName = ingredients.map { it.name }.toPersistentList(),
                    onSuggestionClick = { suggestion: String ->
                        inputIngredientsViewModel.handleIntent(
                        InputIngredientsIntent.SelectSuggestionIngredient(suggestion)
                        ) },
                    width = JustRecipesTheme.dimensions.widthInputtedIngredient,
                    textStyle = JustRecipesTheme.typography.input1,
                    colorField = JustRecipesTheme.colors.background2,
                    colorBorderField = JustRecipesTheme.colors.border2,
                    colorText = JustRecipesTheme.colors.text2,
                    colorSuggestion = JustRecipesTheme.colors.background3,
                    contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                    borderThickness = JustRecipesTheme.dimensions.borderThickness,
                    bottomMenuHeight = JustRecipesTheme.dimensions.heightBottomMenu,
                )
            }
            if (inputIngredientsViewModel.inputTextStateIngredient.text.isEmpty()) {
                InputtedIngredientsShow(
                    inputtedIngredients = inputtedIngredients.toPersistentList(),
                    onDeleteClick = { ingredient: IngredientModel ->
                        inputIngredientsViewModel.handleIntent(
                            InputIngredientsIntent.IsRemoveIngredient(ingredient)
                        ) },
                    onWeightClick = { ingredientId: Int, ingredientWeight: Int ->
                        inputIngredientsViewModel.handleIntent(
                            InputIngredientsIntent.ChangeWeightIngredient(ingredientId, ingredientWeight)
                        ) },
                    iconDeleteIngredient = R.drawable.round_do_not_disturb_on_24,
                    descriptionIconDeleteIngredient = R.string.delete_inputted_ingredient,
                    colorIconDeleteIngredient = JustRecipesTheme.colors.iconDeleteIngredient,
                    colorInputtedIngredientsField = JustRecipesTheme.colors.background4,
                    colorInputtedIngredientText = JustRecipesTheme.colors.text4,
                    textStyleInputtedIngredient = JustRecipesTheme.typography.title1,
                    iconScale = R.drawable.outline_scale_24,
                    descriptionIconScale = R.string.scale,
                    colorBackgroundWeightIngredient = JustRecipesTheme.colors.background2,
                    colorWeightIngredient = JustRecipesTheme.colors.text5,
                    textStyleWeightIngredient = JustRecipesTheme.typography.input2,
                    contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                    width = JustRecipesTheme.dimensions.widthInputtedIngredient,
                    bottomMenuHeight = JustRecipesTheme.dimensions.heightBottomMenu,
                    widthInputtedIngredientField = JustRecipesTheme.dimensions.widthInputtedIngredientField,
                    widthInputtedIngredientText = JustRecipesTheme.dimensions.widthInputtedIngredientText,
                    widthInputtedIngredientWeight = JustRecipesTheme.dimensions.widthInputtedIngredientWeight,
                    heightInputtedIngredientWeight = JustRecipesTheme.dimensions.heightInputtedIngredientWeight,
                    sizeIcon = JustRecipesTheme.dimensions.sizeIcon1,
                    sizeIconScale = JustRecipesTheme.dimensions.sizeIcon2,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField
                )
            }
            if (inputIngredientsUiState.isDeleteIngredient) {
                CustomDialog(
                    onDismissRequest = { inputIngredientsViewModel.handleIntent(
                        InputIngredientsIntent.DismissRemoveIngredient) },
                    onConfirmation = { inputIngredientsViewModel.handleIntent(
                        InputIngredientsIntent.RemoveInputtedIngredient) },
                    heightDialog = JustRecipesTheme.dimensions.heightDialog,
                    widthDialog = JustRecipesTheme.dimensions.widthDialog,
                    colorBackground = JustRecipesTheme.colors.background4,
                    colorBorder = JustRecipesTheme.colors.text4,
                    colorText = JustRecipesTheme.colors.text4,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                    borderThickness = JustRecipesTheme.dimensions.borderThickness,
                    textDialog = stringResource(R.string.delete_ingredient),
                    item = inputIngredientsUiState.deletingIngredientName,
                    textStyle = JustRecipesTheme.typography.title1,
                    textConfirmation = stringResource(R.string.confirmation),
                    textDismiss = stringResource(R.string.dismiss)
                )
            }
            if (inputIngredientsUiState.isIngredientInputted) {
                CustomPopup(
                    onDismissRequest = { inputIngredientsViewModel.handleIntent(
                        InputIngredientsIntent.IsIngredientInputted) },
                    contentPadding = JustRecipesTheme.dimensions.paddingFieldInput,
                    widthPopup = JustRecipesTheme.dimensions.widthPopup,
                    colorBackground = JustRecipesTheme.colors.background4,
                    colorBorder = JustRecipesTheme.colors.text4,
                    colorText = JustRecipesTheme.colors.text4,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                    borderThickness = JustRecipesTheme.dimensions.borderThickness,
                    textStyle = JustRecipesTheme.typography.title1,
                    textPopupPre = stringResource(R.string.ingredient),
                    textPopupAft = stringResource(R.string.already_exist),
                    item = inputIngredientsUiState.newIngredientName
                )
            }
            if (inputIngredientsUiState.isIngredientNew) {
                MakeNewIngredient(
                    onDismissRequest = { inputIngredientsViewModel.handleIntent(
                        InputIngredientsIntent.DismissNewIngredient) },
                    onConfirmation = { ingredientCategory: String ->
                        inputIngredientsViewModel.handleIntent(
                            InputIngredientsIntent.AddNewIngredient(ingredientCategory)
                        ) },
                    selectedIndex = inputIngredientsViewModel.selectedIndexCategory,
                    heightDialog = JustRecipesTheme.dimensions.heightNewIngredientDialog,
                    widthDialog = JustRecipesTheme.dimensions.widthNewIngredientDialog,
                    colorBackground = JustRecipesTheme.colors.background4,
                    colorStroke = JustRecipesTheme.colors.text4,
                    colorText = JustRecipesTheme.colors.text4,
                    radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                    borderThickness = JustRecipesTheme.dimensions.borderThickness,
                    textDialogPre = stringResource(R.string.add_unknown_ingredient),
                    item = inputIngredientsUiState.newIngredientName,
                    textDialogAft = stringResource(R.string.select_category_ingredient),
                    textStyle = JustRecipesTheme.typography.title1,
                    textStyleCategory = JustRecipesTheme.typography.input1,
                    textConfirmation = stringResource(R.string.confirmation),
                    textDismiss = stringResource(R.string.dismiss),
                    contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                    colorBackgroundCategory = JustRecipesTheme.colors.background2,
                    colorBorderCategory = JustRecipesTheme.colors.border2,
                    colorTextCategory = JustRecipesTheme.colors.text2,
                    listCategory = ingredients.map { it.category }.distinct().sorted().toPersistentList(),
                    colorBackgroundCategorySelected = JustRecipesTheme.colors.background3
                )
            }


        }
        */
    }
}


package com.alexit.justrecipes.presentation.feature.inputingrediets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.common.SourceState
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.presentation.components.CircleLoader
import com.alexit.justrecipes.presentation.components.CustomDialog
import com.alexit.justrecipes.presentation.components.CustomPopup
import com.alexit.justrecipes.presentation.components.CustomTextField
import com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel.InputIngredientsIntent
import com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel.InputIngredientsViewModel
import com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel.NotifySideEffect
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun InputIngredientsScreen(
   inputIngredientsViewModel: InputIngredientsViewModel = hiltViewModel()
) {
    val inputIngredientsUiState by inputIngredientsViewModel.uiState.collectAsStateWithLifecycle()
    val ingredientsNameState = inputIngredientsViewModel.ingredientsNameState.collectAsStateWithLifecycle()
    val inputtedIngredientsState = inputIngredientsViewModel.inputtedIngredientsState.collectAsStateWithLifecycle()

    var isNewNotify by remember { mutableStateOf(false) }
    var notifyMessage by remember { mutableStateOf("") }
    var notifyState by remember { mutableStateOf(NotifyState.INFO) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        inputIngredientsViewModel.sideEffect.collectLatest { notify ->
            when (notify) {
                is NotifySideEffect.ShowNotify -> {
                    isNewNotify = true
                    notifyMessage = "${notify.message.asString(context)}\n${notify.addition}".trimEnd()
                    notifyState = notify.state
                }
            }
        }
    }

    if (isNewNotify) {
        CustomPopup(
            message = notifyMessage,
            state = notifyState,
            onDismissRequest = { isNewNotify = !isNewNotify }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
        ) {
        TitlePanel(
            text = stringResource(R.string.title_input_ingredients),
            //onLeftClick = {}
        )

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
                textStyle = JustRecipesTheme.typography.text2,
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
                when(val stateSource = ingredientsNameState.value) {
                    is SourceState.Loading -> LoadingScreen()
                    is SourceState.Success -> SuggestionsIngredientsShow(
                        state = inputIngredientsViewModel.inputTextStateIngredient,
                        ingredientsName = stateSource.data.toPersistentList(),
                        onSuggestionClick = { suggestion: String ->
                            inputIngredientsViewModel.handleIntent(
                                InputIngredientsIntent.SelectSuggestionIngredient(suggestion)
                            )
                        },
                        width = JustRecipesTheme.dimensions.widthInputtedIngredient,
                        textStyle = JustRecipesTheme.typography.text2,
                        colorField = JustRecipesTheme.colors.background2,
                        colorBorderField = JustRecipesTheme.colors.border2,
                        colorText = JustRecipesTheme.colors.text2,
                        colorSuggestion = JustRecipesTheme.colors.background3,
                        contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                        radiusShape = JustRecipesTheme.dimensions.radiusCornerField,
                        borderThickness = JustRecipesTheme.dimensions.borderThickness,
                        bottomMenuHeight = JustRecipesTheme.dimensions.heightBottomMenu
                    )
                    is SourceState.Error -> {
                        isNewNotify = true
                        notifyMessage = if (stateSource.message != null) {
                            "${ stringResource(R.string.hardware_error)}\n${ stateSource.message }"
                        } else {
                            stringResource(R.string.unknown_error_occurred)
                        }
                        notifyState = NotifyState.DANGER
                    }

                }
            }
            if (inputIngredientsViewModel.inputTextStateIngredient.text.isEmpty()) {
                when(val stateSource = inputtedIngredientsState.value) {
                    is SourceState.Loading -> LoadingScreen()
                    is SourceState.Success -> ShowInputtedIngredients(
                        inputtedIngredients = stateSource.data.toPersistentList(),
                        onDeleteClick = { ingredient: IngredientModel ->
                            inputIngredientsViewModel.handleIntent(
                                InputIngredientsIntent.IsRemoveIngredient(ingredient)
                            )
                        },
                        onWeightClick = { ingredientId: Int, ingredientWeight: Int, ingredientName: String ->
                            inputIngredientsViewModel.handleIntent(
                                InputIngredientsIntent.ChangeWeightIngredient(
                                    ingredientId,
                                    ingredientWeight,
                                    ingredientName
                                )
                            )
                        },
                        iconDeleteIngredient = R.drawable.round_do_not_disturb_on_24,
                        descriptionIconDeleteIngredient = R.string.delete_inputted_ingredient,
                        colorIconDeleteIngredient = JustRecipesTheme.colors.iconDeleteIngredient,
                        colorInputtedIngredientsField = JustRecipesTheme.colors.background4,
                        colorInputtedIngredientText = JustRecipesTheme.colors.text4,
                        textStyleInputtedIngredient = JustRecipesTheme.typography.text1,
                        iconScale = R.drawable.outline_scale_24,
                        descriptionIconScale = R.string.icon_scale,
                        colorBackgroundWeightIngredient = JustRecipesTheme.colors.background2,
                        colorWeightIngredient = JustRecipesTheme.colors.text5,
                        textStyleWeightIngredient = JustRecipesTheme.typography.text3,
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
                    is SourceState.Error -> {
                        isNewNotify = true
                        notifyMessage = if (stateSource.message != null) {
                            "${ stringResource(R.string.hardware_error)}\n${ stateSource.message }"
                        } else {
                            stringResource(R.string.unknown_error_occurred)
                        }
                        notifyState = NotifyState.DANGER
                    }
                }
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
                    textStyle = JustRecipesTheme.typography.text1,
                    textConfirmation = stringResource(R.string.confirmation),
                    textDismiss = stringResource(R.string.dismiss)
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
                    textStyle = JustRecipesTheme.typography.text1,
                    textStyleCategory = JustRecipesTheme.typography.text2,
                    textConfirmation = stringResource(R.string.confirmation),
                    textDismiss = stringResource(R.string.dismiss),
                    contentPadding = JustRecipesTheme.dimensions.contentPaddingField,
                    colorBackgroundCategory = JustRecipesTheme.colors.background2,
                    colorBorderCategory = JustRecipesTheme.colors.border2,
                    colorTextCategory = JustRecipesTheme.colors.text2,
                    listCategory = inputIngredientsUiState.categories.toPersistentList(),
                    colorBackgroundCategorySelected = JustRecipesTheme.colors.background3
                )
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
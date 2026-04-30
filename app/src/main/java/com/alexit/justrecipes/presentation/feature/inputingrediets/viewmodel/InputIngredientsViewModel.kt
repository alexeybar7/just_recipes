package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.usecase.AddInputtedIngredientUseCase
import com.alexit.justrecipes.domain.usecase.AddNewIngredientUseCase
import com.alexit.justrecipes.domain.usecase.ChangeWeightIngredientUseCase
import com.alexit.justrecipes.domain.usecase.GetCategoriesUseCase
import com.alexit.justrecipes.domain.usecase.GetIngredientUseCase
import com.alexit.justrecipes.domain.usecase.GetInputtedIngredientsUseCase
import com.alexit.justrecipes.domain.usecase.GetSuggestionsUseCase
import com.alexit.justrecipes.domain.usecase.RemoveInputtedIngredientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputIngredientsViewModel @Inject constructor(
    private val getIngredientUseCase: GetIngredientUseCase,
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val getInputtedIngredientsUseCase: GetInputtedIngredientsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addNewIngredientUseCase: AddNewIngredientUseCase,
    private val addInputtedIngredientUseCase: AddInputtedIngredientUseCase,
    private val removeInputtedIngredientUseCase: RemoveInputtedIngredientUseCase,
    private val changeWeightIngredientUseCase: ChangeWeightIngredientUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(InputIngredientsUiState())
    val uiState: StateFlow<InputIngredientsUiState> = _uiState.asStateFlow()

    //val suggestionsPaging by lazy { getSuggestionsUseCase().cachedIn(viewModelScope) }
    val inputtedIngredientsState: StateFlow<List<IngredientModel>> by lazy {
        getInputtedIngredientsUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )
    }

    val suggestions: StateFlow<List<String>> by lazy {
        getSuggestionsUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )
    }

    val inputTextStateIngredient = TextFieldState()

    fun handleIntent(intent: InputIngredientsIntent) {
        when (intent) {
            is InputIngredientsIntent.SelectSuggestionIngredient -> selectSuggestionIngredient(intent.suggestion)
            is InputIngredientsIntent.CheckingSelectedIngredient -> checkingSelectedIngredient(intent.ingredientName)
            is InputIngredientsIntent.IsIngredientInputted -> isIngredientInputted()
            is InputIngredientsIntent.AddNewIngredient -> addNewIngredient(intent.ingredientCategory)
            is InputIngredientsIntent.DismissNewIngredient -> dismissNewIngredient()
            is InputIngredientsIntent.IsRemoveIngredient -> isRemoveIngredient(intent.ingredient)
            is InputIngredientsIntent.RemoveInputtedIngredient -> removeInputtedIngredient()
            is InputIngredientsIntent.DismissRemoveIngredient -> dismissRemoveIngredient()
            is InputIngredientsIntent.ChangeWeightIngredient -> changeWeightIngredient(intent.ingredientId, intent.ingredientWeight)
        }
    }

    private fun selectSuggestionIngredient(suggestion: String) {
        inputTextStateIngredient.setTextAndPlaceCursorAtEnd(suggestion)
    }

    private fun checkingSelectedIngredient(ingredientName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val addingIngredient: IngredientModel? = getIngredientUseCase(ingredientName)
            if (
                addingIngredient != null &&
                !inputtedIngredientsState.value.contains(addingIngredient)
            ) {
                addInputtedIngredientUseCase(addingIngredient.id)
                inputTextStateIngredient.clearText()
            } else if (
                addingIngredient != null
                ) {
                inputTextStateIngredient.clearText()
                _uiState.update { currentState ->
                    currentState.copy(
                        isIngredientInputted = true,
                        alreadyInputtedIngredientName = addingIngredient.name
                    )
                }
            } else {
                val categories = getCategoriesUseCase()
                    _uiState.update { currentState ->
                        currentState.copy(
                            isIngredientNew = true,
                            newIngredientName = ingredientName,
                            categories = categories
                )
            }
        }
    }
    }

    private fun isIngredientInputted() {
        _uiState.update { currentState ->
            currentState.copy(
            isIngredientInputted = false,
            alreadyInputtedIngredientName = ""
            )
        }
    }

    private fun addNewIngredient(ingredientCategory: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewIngredientUseCase(
                uiState.value.newIngredientName,
                ingredientCategory)
            _uiState.update { currentState ->
                currentState.copy(
                    isIngredientNew = false,
                    newIngredientName = ""
                )
            }
            inputTextStateIngredient.clearText()
        }
    }

    private fun dismissNewIngredient() {
        _uiState.update { currentState ->
            currentState.copy(
                isIngredientNew = false,
                newIngredientName = ""
            )
        }
    }

    private fun isRemoveIngredient(ingredient: IngredientModel) {
        _uiState.update { currentState ->
            currentState.copy(
                isDeleteIngredient = true,
                deletingIngredientId = ingredient.id,
                deletingIngredientName = ingredient.name
            )
        }
    }

    fun dismissRemoveIngredient() {
        _uiState.update { currentState ->
            currentState.copy(
                isDeleteIngredient = false,
                deletingIngredientId = -1,
                deletingIngredientName = ""
            )
        }
    }
    private fun removeInputtedIngredient() {
        viewModelScope.launch(Dispatchers.IO) {
            removeInputtedIngredientUseCase(uiState.value.deletingIngredientId)
            _uiState.update { currentState ->
                currentState.copy(
                    isDeleteIngredient = false,
                    deletingIngredientId = -1,
                    deletingIngredientName = ""
                )
            }
        }
    }

    private fun changeWeightIngredient(ingredientId: Int, ingredientWeight: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            changeWeightIngredientUseCase(ingredientId, ingredientWeight)
        }
    }
}

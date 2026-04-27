package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.domain.usecase.AddNewIngredientUseCase
import com.alexit.justrecipes.domain.usecase.GetIngredientsUseCase
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.usecase.AddInputtedIngredientUseCase
import com.alexit.justrecipes.domain.usecase.ChangeWeightIngredientUseCase
import com.alexit.justrecipes.domain.usecase.RemoveInputtedIngredientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputIngredientsViewModel @Inject constructor(
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val addNewIngredientUseCase: AddNewIngredientUseCase,
    private val addInputtedIngredientUseCase: AddInputtedIngredientUseCase,
    private val removeInputtedIngredientUseCase: RemoveInputtedIngredientUseCase,
    private val changeWeightIngredientUseCase: ChangeWeightIngredientUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(InputIngredientsUiState())
    val uiState: StateFlow<InputIngredientsUiState> = _uiState.asStateFlow()
    val ingredients: StateFlow<List<IngredientModel>> by lazy {
        getIngredientsUseCase().stateIn(
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
        val addingIngredient: IngredientModel? = ingredients.value.find { it.name == ingredientName }
        if (
            addingIngredient != null &&
            !addingIngredient.isInputted
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                addInputtedIngredientUseCase(addingIngredient.id)
            }
            inputTextStateIngredient.clearText()
        } else if (
            addingIngredient != null
            ) {
            inputTextStateIngredient.clearText()
            _uiState.update { currentState ->
                currentState.copy(
                    alreadyInputtedIngredientName = ingredientName,
                    isIngredientInputted = true
                )
            }
        } else {
        _uiState.update { currentState ->
            currentState.copy(
                newIngredientId = ingredients.value.size + 1,
                newIngredientName = ingredientName,
                isIngredientNew = true
            )
        }
    }
    }

    private fun isIngredientInputted() {
        _uiState.update { it.copy(
            isIngredientInputted = false,
            alreadyInputtedIngredientName = ""
        ) }
    }

    private fun addNewIngredient(ingredientCategory: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewIngredientUseCase(
                uiState.value.newIngredientId,
                uiState.value.newIngredientName,
                ingredientCategory)
            addInputtedIngredientUseCase(uiState.value.newIngredientId)
            _uiState.update { currentState ->
                currentState.copy(
                    newIngredientId = -1,
                    newIngredientName = "",
                    isIngredientNew = false
                )
            }
            inputTextStateIngredient.clearText()
        }
    }

    private fun dismissNewIngredient() {
        _uiState.update { currentState ->
            currentState.copy(
                newIngredientId = -1,
                newIngredientName = "",
                isIngredientNew = false
            )
        }
    }

    private fun isRemoveIngredient(ingredient: IngredientModel) {
        _uiState.update { it.copy(
            isDeleteIngredient = true,
            deletingIngredientId = ingredient.id,
            deletingIngredientName = ingredient.name
        ) }
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

    val selectedIndexCategory = mutableIntStateOf(-1)
}

package com.alexit.justrecipes.presentation.feature.inputingrediets

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.data.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val recipesRepository: RecipesRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(InputIngredientsUiState())
    val uiState: StateFlow<InputIngredientsUiState> = _uiState.asStateFlow()

    val ingredients: StateFlow<List<IngredientModel>> = recipesRepository.getIngredients().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf()
    )

    val inputtedIngredients: StateFlow<List<InputtedIngredients>> = recipesRepository.getInputtedIngredients().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        listOf()
    )

    val inputTextStateIngredient = TextFieldState()

    val addingIngredient: MutableState<OwnIngredient> =
        mutableStateOf(OwnIngredient(id = -1, name = "", category = "")
        )

    val deletingIngredient: MutableState<InputtedIngredients> =
        mutableStateOf(InputtedIngredients(id = -1, name = "", category = "", weight = null)
        )

    val selectedIndexCategory = mutableIntStateOf(-1)

    fun selectSuggestionIngredient(suggestion: String) {
        inputTextStateIngredient.setTextAndPlaceCursorAtEnd(suggestion)
    }

    fun addInputtedIngredient(ingredientName: String) {
        if (ingredients.value.any {it.name == ingredientName} &&
            !inputtedIngredients.value.any { it.name == ingredientName }
            ) {
            val addingIngredient = ingredients.value.find { it.name == ingredientName } !!
            viewModelScope.launch {
                recipesRepository.addInputtedIngredient(addingIngredient)
            }
            inputTextStateIngredient.clearText()
        } else if (inputtedIngredients.value.any { it.name == ingredientName }) {
            addingIngredient.value = inputtedIngredients.value.find { it.name == ingredientName } !!
            inputTextStateIngredient.clearText()
            _uiState.update { currentState ->
                currentState.copy(isIngredientInputted = true)
            }
        } else if (ingredients.value.any { it.name != ingredientName }) {
            addingIngredient.value = IngredientModel(
                id = ingredients.value.size + 1,
                name = ingredientName,
                category = ""
            )
            _uiState.update { currentState ->
                currentState.copy(isIngredientNew = true)
            }
        }
    }

    fun updateIsIngredientInputted() {
        _uiState.update { currentState ->
            currentState.copy(isIngredientInputted = false)
        }
    }

    fun addNewIngredient(category: String) {
        addingIngredient.value = IngredientModel(
            id = addingIngredient.value.id,
            name = addingIngredient.value.name,
            category = category
        )
        recipesRepository.addIngredient(addingIngredient.value)
        recipesRepository.addInputtedIngredient(addingIngredient.value)
        selectedIndexCategory.intValue = -1
        updateIsIngredientNew()
    }

    fun updateIsIngredientNew() {
        inputTextStateIngredient.clearText()
        _uiState.update { currentState ->
            currentState.copy(isIngredientNew = false)
        }
    }

    fun updateIsDeleteIngredient(ingredient: IngredientModel) {
        deletingIngredient.value = ingredient
        _uiState.update { currentState ->
            currentState.copy(isDeleteIngredient = true)
        }
    }

    fun deleteInputtedIngredient() {
        recipesRepository.deleteInputtedIngredient(deletingIngredient.value)
        _uiState.update { currentState ->
            currentState.copy(isDeleteIngredient = false)
        }
    }

    fun dismissDeleteIngredient() {
        _uiState.update { currentState ->
            currentState.copy(isDeleteIngredient = false)
        }
    }

    fun updateWeightIngredient(id: Int, weight: Int) {
        recipesRepository.updateWeightIngredient(id, weight)
    }
}
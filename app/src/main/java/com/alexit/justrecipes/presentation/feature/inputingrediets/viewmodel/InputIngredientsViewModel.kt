package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.data.repository.ResourcesState
import com.alexit.justrecipes.domain.model.IngredientModel
import com.alexit.justrecipes.domain.usecase.AddIngredientUseCase
import com.alexit.justrecipes.domain.usecase.AddInputtedIngredientUseCase
import com.alexit.justrecipes.domain.usecase.ChangeWeightIngredientUseCase
import com.alexit.justrecipes.domain.usecase.GetIngredientsUseCase
import com.alexit.justrecipes.domain.usecase.GetInputtedIngredientsUseCase
import com.alexit.justrecipes.domain.usecase.RemoveInputtedIngredientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InputIngredientsViewModel @Inject constructor(
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val addIngredientUseCase: AddIngredientUseCase,
    private val getInputtedIngredientsUseCase: GetInputtedIngredientsUseCase,
    private val addInputtedIngredientUseCase: AddInputtedIngredientUseCase,
    private val removeInputtedIngredientUseCase: RemoveInputtedIngredientUseCase,
    private val changeWeightIngredientUseCase: ChangeWeightIngredientUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(InputIngredientsUiState())
    val uiState: StateFlow<InputIngredientsUiState> = _uiState
    private val _effectChannel = Channel<SnackbarEffect>()
    val effectChannel: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()

    val inputTextStateIngredient = TextFieldState()

    //init {
    //    handleIntent(InputIngredientsIntent.LoadIngredients)
    //}

    fun handleIntent(intent: InputIngredientsIntent) {
        when (intent) {
            is InputIngredientsIntent.LoadIngredients -> loadIngredients()
            is InputIngredientsIntent.LoadInputtedIngredients -> loadInputtedIngredients()
            is InputIngredientsIntent.SelectSuggestionIngredient -> selectSuggestionIngredient(intent.suggestion)
            is InputIngredientsIntent.CheckingSelectedIngredient -> checkingSelectedIngredient(intent.ingredientName)
            is InputIngredientsIntent.IsIngredientInputted -> updateIsIngredientInputted()
            is InputIngredientsIntent.AddNewIngredient -> addNewIngredient(intent.ingredientCategory)
            is InputIngredientsIntent.AddInputtedIngredient -> addInputtedIngredient(intent.ingredientId)
            is InputIngredientsIntent.IsRemoveIngredient -> updateIsRemoveIngredient(intent.ingredient)
            is InputIngredientsIntent.RemoveInputtedIngredient -> removeInputtedIngredient()
            is InputIngredientsIntent.ChangeWeightIngredient -> changeWeightIngredient(intent.ingredientId, intent.ingredientWeight)
        }
    }

    private fun loadIngredients() {
        viewModelScope.launch(Dispatchers.IO) {
            getIngredientsUseCase().collectLatest { resourcesState ->
                when (resourcesState) {
                    is ResourcesState.Loading -> withContext(Dispatchers.Main) {
                        _uiState.update { it.copy (isLoading = true) }
                    }
                    is ResourcesState.Success -> withContext(Dispatchers.Main) {
                        _uiState.update { it.copy (
                            isLoading = false,
                            ingredients = resourcesState.data
                        ) }
                    }
                    is ResourcesState.Error -> withContext(Dispatchers.Main) {
                        _uiState.update { it.copy (isLoading = false) }
                        _effectChannel.send(SnackbarEffect.ShowSnackbar("Error loading ingredients: ${resourcesState.message}"))
                    }
                }
            }
        }
    }

    private fun loadInputtedIngredients() {
        viewModelScope.launch(Dispatchers.IO) {
            getInputtedIngredientsUseCase().collectLatest { resourcesState ->
                when (resourcesState) {
                    is ResourcesState.Loading -> withContext(Dispatchers.Main) {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is ResourcesState.Success -> withContext(Dispatchers.Main) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                inputtedIngredients = resourcesState.data
                            )
                        }
                    }
                    is ResourcesState.Error -> withContext(Dispatchers.Main) {
                        _uiState.update { it.copy(isLoading = false) }
                        _effectChannel.send(SnackbarEffect.ShowSnackbar("Error loading inputted ingredients: ${resourcesState.message}"))
                    }
                }
            }
        }
    }

    private fun checkingSelectedIngredient(ingredientName: String) {
        val addingIngredient = uiState.value.ingredients.find { it.name == ingredientName }
        if (
            addingIngredient != null &&
            !uiState.value.inputtedIngredients.any { it.name == ingredientName }
        ) {
            viewModelScope.launch {
                addInputtedIngredient(addingIngredient.id)
            }
            inputTextStateIngredient.clearText()
        } else if (uiState.value.inputtedIngredients.any { it.name == ingredientName }) {
            inputTextStateIngredient.clearText()
            _uiState.update { currentState ->
                currentState.copy(
                    alreadyInputtedIngredientName = ingredientName,
                    isIngredientInputted = true
                )
            }
        } else if (addingIngredient == null) {
            _uiState.update { currentState ->
                val newIngredientId = uiState.value.ingredients.size + 1
                currentState.copy(
                    newIngredientId = newIngredientId,
                    newIngredientName = ingredientName,
                    isIngredientNew = true
                )
            }
        }
    }

    private fun addInputtedIngredient(ingredientId: Int) {
        _uiState.update { it.copy (isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addInputtedIngredientUseCase(ingredientId)
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy (isLoading = false) }
                    _effectChannel.send(SnackbarEffect.ShowSnackbar("Inputted ingredient added successfully!"))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy (isLoading = false) }
                    _effectChannel.send(SnackbarEffect.ShowSnackbar("Error adding inputted ingredient: ${e.message}"))
                }
            }
        }
    }

    private fun addNewIngredient(ingredientCategory: String) {
        _uiState.update { it.copy (isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addIngredientUseCase(
                    uiState.value.newIngredientId,
                    uiState.value.newIngredientName,
                    ingredientCategory)
                addInputtedIngredient(uiState.value.newIngredientId)
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy (
                        isLoading = false,
                        isIngredientNew = false,
                        newIngredientId = -1,
                        newIngredientName = "",
                        selectedIndexCategory = -1
                        )
                    }
                    _effectChannel.send(SnackbarEffect.ShowSnackbar("New ingredient added successfully!"))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy (isLoading = false) }
                    _effectChannel.send(SnackbarEffect.ShowSnackbar("Error adding new ingredient: ${e.message}"))
                }
            }
        }
    }

    private fun removeInputtedIngredient() {
        _uiState.update { it.copy (isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                removeInputtedIngredientUseCase(uiState.value.deletingIngredientId)
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy (
                        isLoading = false,
                        isDeleteIngredient = false,
                        deletingIngredientId = -1,
                        deletingIngredientName = ""
                    ) }
                    _effectChannel.send(SnackbarEffect.ShowSnackbar("Inputted ingredient removed successfully!"))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy (isLoading = false) }
                    _effectChannel.send(SnackbarEffect.ShowSnackbar("Error removing inputted ingredient: ${e.message}"))
                }
            }
        }
    }

    private fun changeWeightIngredient(ingredientId: Int, ingredientWeight: Int) {
        _uiState.update { it.copy (isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                changeWeightIngredientUseCase(ingredientId, ingredientWeight)
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy (isLoading = false) }
                    _effectChannel.send(SnackbarEffect.ShowSnackbar("Weight ingredient changed successfully!"))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy (isLoading = false) }
                    _effectChannel.send(SnackbarEffect.ShowSnackbar("Error changing weight ingredient: ${e.message}"))
                }
            }
        }
    }

    private fun updateIsIngredientInputted() {
        _uiState.update { it.copy(
            isIngredientInputted = false,
            alreadyInputtedIngredientName = ""
        ) }
    }

    private fun updateIsRemoveIngredient(ingredient: IngredientModel) {
        _uiState.update { it.copy(
            isDeleteIngredient = true,
            deletingIngredientId = ingredient.id,
            deletingIngredientName = ingredient.name
        ) }
    }

    fun dismissDeleteIngredient() {
        _uiState.update { currentState ->
            currentState.copy(
                isDeleteIngredient = false,
                deletingIngredientId = -1,
                deletingIngredientName = ""
            )
        }
    }

    val selectedIndexCategory = mutableIntStateOf(-1)

    private fun selectSuggestionIngredient(suggestion: String) {
        inputTextStateIngredient.setTextAndPlaceCursorAtEnd(suggestion)
    }
}
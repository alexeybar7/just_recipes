package com.alexit.justrecipes.presentation.feature.searchrecipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexit.justrecipes.R
import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.common.StringResourceHolder
import com.alexit.justrecipes.domain.model.database.RecipeModelFull
import com.alexit.justrecipes.domain.usecase.GetRecipeFullDataUseCase
import com.alexit.justrecipes.presentation.components.NotifySideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowRecipeViewModel @Inject constructor(
    private val getRecipeFullDataUseCase: GetRecipeFullDataUseCase
) : ViewModel() {
    private val _recipeState = MutableStateFlow<RecipeModelFull?>(null)
    val recipeState: StateFlow<RecipeModelFull?> = _recipeState.asStateFlow()

    private val _sideEffect = Channel<NotifySideEffect>()
    val sideEffect: Flow<NotifySideEffect> = _sideEffect.receiveAsFlow()

    fun getRecipe(recipeId: Int) {
        viewModelScope.launch {
            try {
                val recipe = getRecipeFullDataUseCase(recipeId)
                _recipeState.value = recipe
            }
            catch (_: Exception) {
                _sideEffect.send(
                    NotifySideEffect.ShowNotify(
                        message = StringResourceHolder.StringResource(R.string.hardware_error_get_recipe),
                        addition = recipeId.toString(),
                        state = NotifyState.DANGER
                    )
                )
            }
        }
    }
}
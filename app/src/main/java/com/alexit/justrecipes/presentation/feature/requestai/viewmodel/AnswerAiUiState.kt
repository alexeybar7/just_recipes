package com.alexit.justrecipes.presentation.feature.requestai.viewmodel

import com.alexit.justrecipes.domain.model.ai.RecipeAi
import com.alexit.justrecipes.domain.model.ai.ResponseAi

data class AnswerAiUiState(
    val responseAi: ResponseAi? = null,
    val recipeAi: RecipeAi? = null,
    val isRecipeAiOk: Boolean = false,
    val isSaved: Boolean = false
)

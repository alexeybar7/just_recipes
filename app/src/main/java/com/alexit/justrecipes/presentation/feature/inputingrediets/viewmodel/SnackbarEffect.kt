package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

sealed class SnackbarEffect {
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : SnackbarEffect()
}
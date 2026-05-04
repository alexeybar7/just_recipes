package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import com.alexit.justrecipes.common.NotifyState

sealed class NotifySideEffect {
    data class ShowNotify(val message: String, val state: NotifyState) : NotifySideEffect()
}
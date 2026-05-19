package com.alexit.justrecipes.presentation.feature.inputingrediets.viewmodel

import com.alexit.justrecipes.common.NotifyState
import com.alexit.justrecipes.common.StringResourceHolder

sealed class NotifySideEffect {
    data class ShowNotify(
        val message: StringResourceHolder,
        val addition: String = "",
        val state: NotifyState
    ) : NotifySideEffect()
}
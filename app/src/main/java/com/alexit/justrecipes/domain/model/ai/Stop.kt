package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.Serializable

@Serializable
data class Stop(
    val reasons: List<Message>
) {
    override fun toString(): String =
        "StopReason(reasons=$reasons)"
}
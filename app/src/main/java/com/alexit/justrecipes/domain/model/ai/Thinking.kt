package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thinking(
    val type: ThinkingType
) {
    override fun toString(): String = "Thinking(type=$type)"
}

@Serializable
enum class ThinkingType {
    @SerialName("enabled")
    ENABLED,

    @SerialName("disabled")
    DISABLED,
}
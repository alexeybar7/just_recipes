package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToolChoice(
    val type: ToolChoiceType
) {
    override fun toString(): String = "ToolChoice(type=$type)"
}


@Serializable
enum class ToolChoiceType {
    @SerialName("none")
    NONE,

    @SerialName("auto")
    AUTO,

    @SerialName("required")
    REQUIRED
}


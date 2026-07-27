package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: Role,
    val content: String
)

@Serializable
enum class Role {
    @SerialName("user")
    USER,

    @SerialName("system")
    SYSTEM,

    @SerialName("assistant")
    ASSISTANT
}
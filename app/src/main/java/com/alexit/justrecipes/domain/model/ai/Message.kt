package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String = "user",
    val content: String
)

package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.Serializable

@Serializable
data class RequestAi(
    val model: String = "deepseek-v4-pro",
    val message: MessageAi
)

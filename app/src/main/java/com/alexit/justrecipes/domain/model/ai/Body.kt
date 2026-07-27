package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Body(
    val messages: List<Message>,
    val model: String,
    val thinking: Thinking,
    @SerialName("reasoning_effort") val reasoningEffort: String,
    @SerialName("max_tokens") val maxTokens: Int,
    @SerialName("response_format") val responseFormat: ResponseFormat,
    val stream: Boolean,
    val temperature: Double,
    @SerialName("top_p") val topP: Double,
    val logprobs: Boolean
)

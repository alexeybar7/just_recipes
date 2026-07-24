package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Body(
    val model: String = "deepseek-v4-pro",
    val messages: List<Message>,
    val thinking: Thinking = Thinking(ThinkingType.ENABLED),
    @SerialName("reasoning_effort") val reasoningEffort: String = "high",
    @SerialName("max_tokens") val maxTokens: Int = 4096,
    @SerialName("response_format") val responseFormat: ResponseFormat = ResponseFormat(
        ResponseFormatType.JSON_OBJECT
    ),
    val stop: Stop? = null,
    val stream: Boolean = false,
    val strimOptions: StreamOptions? = null,
    val temperature: Double = 1.0,
    @SerialName("top_p") val topP: Double = 1.0,
    val tools: Tools? = null,
    @SerialName("tool_choice") val toolChoice: ToolChoice = ToolChoice(ToolChoiceType.NONE),
    val logprobs: Boolean = false,
    @SerialName("top_logprobs") val topLogprobs: Int? = null
)

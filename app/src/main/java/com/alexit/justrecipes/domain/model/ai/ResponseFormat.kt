package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFormat(
    val type: ResponseFormatType
) {
    override fun toString(): String = "ResponseFormat(type=$type)"
}

@Serializable
enum class ResponseFormatType {
    @SerialName("text")
    TEXT,

    @SerialName("json_object")
    JSON_OBJECT,
}

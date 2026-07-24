package com.alexit.justrecipes.domain.model.ai

import kotlinx.serialization.Serializable

@Serializable
data class StreamOptions(
    val includeUsage: Boolean
) {
    override fun toString(): String =
        "StreamOptions(includeUsage=$includeUsage)"
}

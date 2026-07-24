package com.alexit.justrecipes.domain.usecase

import com.alexit.justrecipes.domain.model.ai.Message
import com.alexit.justrecipes.domain.model.ai.Body
import com.alexit.justrecipes.domain.remote.KtorApiService
import javax.inject.Inject

class GetRecipeAiUseCase @Inject constructor(
    private val ktorApiService: KtorApiService
) {
    suspend operator fun invoke(prompt: String) {
        val messageAi = Message(content = prompt)
        ktorApiService.getRecipeAi(
            Body(
                message = messageAi
            )
        )
    }
}
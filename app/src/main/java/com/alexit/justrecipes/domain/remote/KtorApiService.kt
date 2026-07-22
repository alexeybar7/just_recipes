package com.alexit.justrecipes.domain.remote

import com.alexit.justrecipes.domain.model.ai.ResponseAi
import com.alexit.justrecipes.domain.model.ai.RequestAi
import io.ktor.client.statement.HttpResponse

interface KtorApiService {
    suspend fun getRecipeAi(requestAi: RequestAi): HttpResponse
}
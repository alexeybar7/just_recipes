package com.alexit.justrecipes.domain.remote

import com.alexit.justrecipes.domain.model.ai.Body
import io.ktor.client.statement.HttpResponse

interface KtorApiService {
    suspend fun getRecipeAi(body: Body): HttpResponse
}
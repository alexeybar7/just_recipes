package com.alexit.justrecipes.data.remote

import com.alexit.justrecipes.domain.model.ai.Body
import com.alexit.justrecipes.domain.remote.KtorApiService
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class KtorApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : KtorApiService {
    override suspend fun getRecipeAi(body: Body): HttpResponse {
        val httpResponse = httpClient.post("chat/completions") {
            setBody(body)
        }
        return httpResponse
    }
}
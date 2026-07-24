package com.alexit.justrecipes.data.remote

import com.alexit.justrecipes.NativeLib.NativeLib
import com.alexit.justrecipes.domain.model.ai.Body
import com.alexit.justrecipes.domain.remote.KtorApiService
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject


private val apiKey: String by lazy { NativeLib.getApiKey() }

class KtorApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : KtorApiService {
    override suspend fun getRecipeAi(body: Body): HttpResponse {
        return httpClient.post("https://api.deepseek.com") {
            contentType(ContentType.Application.Json)
            bearerAuth(apiKey)
            setBody(body)
        }
    }
}
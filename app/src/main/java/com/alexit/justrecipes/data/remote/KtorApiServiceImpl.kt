package com.alexit.justrecipes.data.remote

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.alexit.justrecipes.domain.model.ai.ResponseAi
import com.alexit.justrecipes.domain.model.ai.RequestAi
import com.alexit.justrecipes.domain.remote.KtorApiService
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import javax.inject.Inject


val API_KEY =
class KtorApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : KtorApiService {
    override suspend fun getRecipeAi(requestAi: RequestAi): HttpResponse {
        return httpClient.post("https://api.deepseek.com") {
            contentType(ContentType.Application.Json)
            bearerAuth("API_KEY")
            setBody(requestAi)
        }
    }
}
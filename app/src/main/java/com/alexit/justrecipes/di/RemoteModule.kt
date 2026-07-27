package com.alexit.justrecipes.di

import com.alexit.justrecipes.NativeLib.NativeLib
import com.alexit.justrecipes.data.remote.KtorApiServiceImpl
import com.alexit.justrecipes.domain.remote.KtorApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

//val apiKey = "sk-7d64035f41cd4b5eb5826752969119a5"
private val apiKey: String by lazy { NativeLib.getApiKey() }

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 100_000
                connectTimeoutMillis = 100_000
                socketTimeoutMillis = 100_000
            }
            defaultRequest {
                url("https://api.deepseek.com/")
                header(HttpHeaders.Authorization, "Bearer $apiKey")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    @Provides
    @Singleton
    fun provideRequestAi(httpClient: HttpClient): KtorApiService {
        return KtorApiServiceImpl(httpClient)
    }
}
package com.alexit.justrecipes.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class SourceState<out T> {
    object Loading : SourceState<Nothing>()
    data class Success<out T>(val data: T) : SourceState<T>()
    data class Error(val message: String) : SourceState<Nothing>()
}
fun <T> Flow<T>.asSourceState(): Flow<SourceState<T>> {
    return this
        .map<T, SourceState<T>> {
            SourceState.Success(it)
        }
        .onStart {
            emit(SourceState.Loading)
        }
        .catch {
            emit(SourceState.Error(it.message ?: "Unknown error occurred"))
        }
}
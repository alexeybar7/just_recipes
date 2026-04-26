package com.alexit.justrecipes.presentation.feature

sealed class ResourcesState<out T> {
    object Loading : ResourcesState<Nothing>()
    data class Success<out T>(val data: T) : ResourcesState<T>()
    data class Error(val message: String) : ResourcesState<Nothing>()
}
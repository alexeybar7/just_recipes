package com.alexit.justrecipes.data.repository

sealed class ResourcesState<out T> {
    data class Success<out T>(val data: T) : ResourcesState<T>()
    data class Error(val message: String) : ResourcesState<Nothing>()
    object Loading : ResourcesState<Nothing>()
}
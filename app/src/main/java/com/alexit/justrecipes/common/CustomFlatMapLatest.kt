package com.alexit.justrecipes.common

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

fun <T, R> Flow<T>.customFlatMapLatest(transform: suspend (T) -> Flow<R>): Flow<R> = channelFlow {
    var previousJob: Job? = null
    collect { value ->
        previousJob?.cancel()
        previousJob = launch {
            transform(value).collect { send(it) }
        }
    }
}
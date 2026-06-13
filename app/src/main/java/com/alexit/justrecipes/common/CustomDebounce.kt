package com.alexit.justrecipes.common

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

fun <T> Flow<T>.customDebounce(timeMillis: Long): Flow<T> = channelFlow {
    var queryJob: Job? = null
    collect { value ->
        queryJob?.cancel()
        queryJob = launch {
            delay(timeMillis)
            send(value)
        }
    }
}
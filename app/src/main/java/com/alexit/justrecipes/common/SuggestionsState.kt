package com.alexit.justrecipes.common

import androidx.compose.ui.text.AnnotatedString
import com.alexit.justrecipes.utility.GSuffArray
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuggestionsState(scope: CoroutineScope, val items: PersistentList<String>) {
    private val _gsa = MutableStateFlow(GSuffArray(""))
    val gsa: StateFlow<GSuffArray> = _gsa.asStateFlow()
    val suggestions: MutableStateFlow<List<AnnotatedString>> = MutableStateFlow(emptyList())

    init {
        scope.launch {
            withContext(Dispatchers.Default) {
                _gsa.value = GSuffArray(*items.toTypedArray())
            }
        }
    }

    fun findMatchingIndex(query: String): Map<Int, List<Int>> {
        if (query.isEmpty()) return emptyMap()

        return gsa.value.index(query)
            .groupBy { (_, itemIndex) -> itemIndex } // group by index to yield a map of index to a list of index and character positions
            .mapValues { (_, list) -> list.map { (start, _) -> start } } // generate new map of index(key) to list of character positions (Values)
    }
}
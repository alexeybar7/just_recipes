package com.alexit.justrecipes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.serialization.NavKeySerializer
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer

@Composable
fun rememberRecipesNavigationState(
    startTab: NavKey,
    tabs: Set<NavKey>
): RecipesNavigationState {
    val currentTab = rememberSerializable( startTab, tabs, serializer = MutableStateSerializer(
        NavKeySerializer())) {
        mutableStateOf(startTab)
    }
    val tabsStacks = tabs.associateWith { tab -> rememberNavBackStack(tab) }

    return remember(startTab, tabs) {
        RecipesNavigationState(
            startTab = startTab,
            _currentTab = currentTab,
            tabStacks = tabsStacks
        )
    }
}

class RecipesNavigationState (
    val startTab: NavKey,
    private val _currentTab : MutableState<NavKey>,
    val tabStacks: Map<NavKey, NavBackStack<NavKey>>
) {
    var currentTab: NavKey
        get() = _currentTab.value
        set(value) { _currentTab.value = value }

    val activeStacks: List<NavKey>
        get() = if (currentTab == startTab) {
            listOf(startTab)
        } else {
            listOf(startTab, currentTab)
        }
    @Composable
    fun toDecoratedEntries(
        entryProvider: (NavKey) -> NavEntry<NavKey>
    ): List<NavEntry<NavKey>> {

        // For each back stack, create a `SaveableStateHolder` decorator and use it to decorate
        // the entries from that stack. When backStacks changes, `rememberDecoratedNavEntries` will
        // be recomposed and a new list of decorated entries is returned.
        val decoratedEntries = tabStacks.mapValues { (_, stack) ->
            val decorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
                rememberViewModelStoreNavEntryDecorator()
            )
            rememberDecoratedNavEntries(
                backStack = stack,
                entryDecorators = decorators,
                entryProvider = entryProvider
            )
        }

        // Only return the entries for the stacks that are currently in use.
        return activeStacks
            .flatMap { decoratedEntries[it] ?: emptyList() }
    }
}

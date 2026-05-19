package com.alexit.justrecipes.presentation.navigation

import androidx.navigation3.runtime.NavKey

class RecipesNavigator(private val state: RecipesNavigationState) {

    fun navigateTo(route: NavKey) {
        if (route in state.tabStacks.keys) {
            state.currentTab = route
        } else {
            state.tabStacks[state.currentTab]?.add(route)
        }
    }

    fun navigationBack(): Boolean {
        val currentStack = state.tabStacks[state.currentTab] ?: return false
        val currentRoute = currentStack.lastOrNull() ?: return false

        // At root of non-start tab? Go back to start tab
        if (currentRoute == state.currentTab && state.currentTab != state.startTab) {
            state.currentTab = state.startTab
            return true
        }

        // At root of start tab? Let the system handle it (exit app)
        if (currentRoute == state.startTab) {
            return false
        }

        currentStack.removeLastOrNull()
        return true
    }

    fun switchTab(tab: NavKey) {
        state.currentTab = tab
    }
}
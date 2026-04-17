package com.alexit.justrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.alexit.justrecipes.presentation.systembarprotection.NavigationBarProtection
import com.alexit.justrecipes.presentation.feature.RecipesScreen
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import com.alexit.justrecipes.presentation.systembarprotection.StatusBarProtection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            window.isNavigationBarContrastEnforced = false
            JustRecipesTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = JustRecipesTheme.colors.background0)
                ) {
                    StatusBarProtection()
                    NavigationBarProtection()
                    RecipesScreen()
                }
            }
        }
    }
}


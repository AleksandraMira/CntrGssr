package com.example.cntrgssr.presentation.game

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.navigation.NavigationNode

object Game : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        GameScreen()
    }
}
package com.example.cntrgssr.presentation.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.navigation.NavigationNode

object Home : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        HomeScreen()
    }
}
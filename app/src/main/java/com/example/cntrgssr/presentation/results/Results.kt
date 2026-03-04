package com.example.cntrgssr.presentation.results

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.navigation.NavigationNode

object Results : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        ResultsScreen()
    }
}
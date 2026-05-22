package com.example.cntrgssr.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

abstract class NavigationNode {
    @Composable
    abstract fun Screen(navController: NavHostController)
}
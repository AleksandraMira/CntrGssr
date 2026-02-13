package com.example.cntrgssr.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

abstract class NavigationNode {
    val route = this::class.qualifiedName.orEmpty()

    @Composable
    abstract fun Screen(navController: NavHostController)
}
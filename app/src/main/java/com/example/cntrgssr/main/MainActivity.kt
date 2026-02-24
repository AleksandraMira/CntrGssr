package com.example.cntrgssr.main

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cntrgssr.presentation.game.Game
import com.example.cntrgssr.presentation.guide.Guide
import com.example.cntrgssr.presentation.home.Home
import com.example.cntrgssr.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                AppNavHost(navController = navController)
            }
        }
    }

    @Composable
    private fun AppNavHost(navController: NavHostController) {
        NavHost (
            modifier = Modifier,
            navController = navController,
            startDestination = Home.route,
        ) {
            composable(Home.route) {
                Home.Screen(navController)
            }
            composable(Guide.route) {
                Guide.Screen(navController)
            }
            composable(Game.route) {
                Game.Screen(navController)
            }
        }
    }
}
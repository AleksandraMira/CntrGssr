package com.example.cntrgssr.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.navigation.NavigationNode
import com.example.cntrgssr.presentation.game.Game
import com.example.cntrgssr.presentation.guide.Guide
import kotlinx.serialization.Serializable
import timber.log.Timber

@Serializable
object Home : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(
            onUserEvent = viewModel::onUserEvent,
        )

        HandleUiEvents(
            viewModel = viewModel,
            navController = navController,
        )
    }

    @Composable
    private fun HandleUiEvents(
        viewModel: HomeViewModel,
        navController: NavHostController,
    ) {
        LaunchedEffect(Unit) {
            viewModel.uiEvent.collect { event ->
                Timber.tag("Home").d("Received UI event: $event")
                when (event) {
                    UiEvent.NavigateToGuide -> navController.navigate(Guide) {
                        launchSingleTop = true
                    }
                    UiEvent.NavigateToGame -> navController.navigate(Game) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    sealed interface UiEvent {
        data object NavigateToGuide : UiEvent
        data object NavigateToGame : UiEvent
    }

    sealed interface UserEvent {
        data object OnGuideButtonClicked : UserEvent
        data object OnStartButtonClicked : UserEvent
    }
}
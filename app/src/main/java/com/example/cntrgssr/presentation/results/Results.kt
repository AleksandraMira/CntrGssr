package com.example.cntrgssr.presentation.results

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.navigation.NavigationNode
import com.example.cntrgssr.presentation.game.Game
import com.example.cntrgssr.presentation.guide.Guide
import com.example.cntrgssr.presentation.home.Home
import com.example.cntrgssr.presentation.home.Home.UiEvent
import com.example.cntrgssr.presentation.home.HomeViewModel
import timber.log.Timber

object Results : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        val viewModel: ResultsViewModel = hiltViewModel()
        ResultsScreen(
            uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
            onUserEvent = viewModel::onUserEvent,
        )
        HandleUiEvents(
            viewModel = viewModel,
            navController = navController,
        )
    }

    @Composable
    private fun HandleUiEvents(
        viewModel: ResultsViewModel,
        navController: NavHostController,
    ) {
        LaunchedEffect(Unit) {
            viewModel.uiEvent.collect { event ->
                Timber.tag("Home").d("Received UI event: $event")
                when (event) {
                    UiEvent.NavigateToHome -> navController.navigate(Home.route) {
                        launchSingleTop = true
                    }
                    UiEvent.NavigateToGame -> navController.navigate(Game.route) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    data class UiState(
        val countryName: String = "",
        val heartNumber: Int = 0,
        val heartPoints: Int = 0,
    )

    sealed interface UiEvent {
        data object NavigateToHome : UiEvent
        data object NavigateToGame : UiEvent
    }

    sealed interface UserEvent {
        data object OnPlayAgainButtonClicked : UserEvent
        data object OnExitButtonClicked : UserEvent
    }
}
package com.example.cntrgssr.presentation.guide

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.navigation.NavigationNode

object Guide : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        val viewModel: GuideViewModel = hiltViewModel()
        GuideScreen(
            onUserEvent = viewModel::onUserEvent,
        )

        HandleUiEvents(
            viewModel = viewModel,
            navController = navController,
        )
    }

    @Composable
    private fun HandleUiEvents(
        viewModel: GuideViewModel,
        navController: NavHostController,
    ) {
        LaunchedEffect(Unit) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    UiEvent.NavigateToHome -> navController.navigateUp()
                }
            }
        }
    }

    sealed interface UiEvent {
        data object NavigateToHome : UiEvent
    }

    sealed interface UserEvent {
        data object OnBackClicked : UserEvent
    }
}
package com.example.cntrgssr.presentation.game

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.navigation.NavigationNode

object Game : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        val viewModel: GameViewModel = hiltViewModel()
        GameScreen(
            onUserEvent = viewModel::onEvent,
            uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        )
    }

    data class UiState(
        val answer: String = "",
        val snackbarMessage: String? = null,
    )

    sealed interface UserEvent {
        data class OnAnswerChange(val answer: String) : UserEvent
        data object OnSubmitAnswer : UserEvent
        data object OnSnackbarShown : UserEvent
    }
}
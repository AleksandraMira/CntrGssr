package com.example.cntrgssr.presentation.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.data.enums.HintType
import com.example.cntrgssr.core.navigation.NavigationNode
import com.example.cntrgssr.presentation.results.Results

object Game : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        val viewModel: GameViewModel = hiltViewModel()
        GameScreen(
            onUserEvent = viewModel::onEvent,
            uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        )
        HandleUiEvents(
            viewModel = viewModel,
            navController = navController,
        )
    }

    @Composable
    private fun HandleUiEvents(
        viewModel: GameViewModel,
        navController: NavHostController,
    ) {
        LaunchedEffect(Unit) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    UiEvent.NavigateToResults -> navController.navigate(Results.route)
                }
            }
        }
    }

    data class UiState(
        val answer: String = "",
        val snackbarMessage: String? = null,
        val heartNumber: Int = 3,
        val isGiveUpDialogVisible: Boolean = false,
        val selectedHint: HintType? = null,
        val availableHintOptions: List<HintType> = HintType.entries,
        val hintLog: Map<HintType, String> = emptyMap(),
        val isLoading: Boolean = false,
    )

    sealed interface UserEvent {
        data class OnAnswerChange(val answer: String) : UserEvent
        data object OnSubmitAnswer : UserEvent
        data object OnSnackbarShown : UserEvent
        data object OnGiveUpButtonClicked : UserEvent
        data object OnGiveUpDialogDismiss : UserEvent
        data object OnGiveUpDialogConfirm : UserEvent
        data class OnHintOptionSelected(val hintType: HintType) : UserEvent
        data object OnHintButtonClicked : UserEvent
    }

    sealed interface UiEvent {
        data object NavigateToResults : UiEvent
    }
}
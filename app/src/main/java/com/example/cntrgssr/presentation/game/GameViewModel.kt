package com.example.cntrgssr.presentation.game

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(Game.UiState())
    val uiState = _uiState.asStateFlow()

     fun onEvent(event: Game.UserEvent) {
        when (event) {
            is Game.UserEvent.OnAnswerChange -> onAnswerChange(event.answer)
            is Game.UserEvent.OnSubmitAnswer -> onSubmitAnswer()
            is Game.UserEvent.OnSnackbarShown -> clearSnackbar()
        }
    }

    private fun onAnswerChange(answer: String) {
        _uiState.update {
            it.copy(
                answer = answer
            )
        }
    }

    private fun onSubmitAnswer() {
        if (uiState.value.answer.isEmpty()) {
            _uiState.update {
                it.copy(
                    snackbarMessage = "Answer cannot be empty"
                )
            }
            return
        }
    }

    private fun clearSnackbar() {
        _uiState.update {
            it.copy(snackbarMessage = null)
        }
    }
}
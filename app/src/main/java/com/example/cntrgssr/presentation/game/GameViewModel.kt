package com.example.cntrgssr.presentation.game

import androidx.compose.material3.TimeInput
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cntrgssr.core.data.dao.CountryDao
import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val countryDao: CountryDao,
    preferencesDataStoreRepository: PreferencesDataStoreRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(Game.UiState())
    val uiState = _uiState.asStateFlow()

    private val country = preferencesDataStoreRepository.countryId
        .map { id ->
            if (id == -1L) null
            else countryDao.getCountryById(id)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null,
        )

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

        if (uiState.value.answer.equals(country.value?.name, ignoreCase = true)) {
            //TODO: Move to results
            _uiState.update {
                it.copy(
                    snackbarMessage = "Correct! The answer is ${country.value?.name}."
                )
            }
        } else {
            //TODO: Remove one heart
            _uiState.update {
                it.copy(
                    snackbarMessage = "Wrong! Try again. The answer is ${country.value?.name}"
                )
            }
        }
    }

    private fun clearSnackbar() {
        _uiState.update {
            it.copy(snackbarMessage = null)
        }
    }
}
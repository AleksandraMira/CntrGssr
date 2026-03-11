package com.example.cntrgssr.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cntrgssr.R
import com.example.cntrgssr.core.data.dao.CountryDao
import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepository
import com.example.cntrgssr.core.util.ResourceResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val countryDao: CountryDao,
    private val resourceResolver: ResourceResolver,
    preferencesDataStoreRepository: PreferencesDataStoreRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(Game.UiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<Game.UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

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
        val answer = uiState.value.answer
        val correctCountryName = country.value?.name
        val currentHeartNumber = uiState.value.heartNumber

        if (answer.isEmpty()) {
            _uiState.update {
                it.copy(
                    snackbarMessage = resourceResolver.getString(R.string.game_screen_empty_answer)
                )
            }
            return
        }

        if (answer.equals(correctCountryName, ignoreCase = true)) {
            viewModelScope.launch {
                _uiEvent.emit(Game.UiEvent.NavigateToResults)
            }
            return
        }

        val newHeartNumber = currentHeartNumber - 1
        if (newHeartNumber == 0) {
            viewModelScope.launch {
                _uiEvent.emit(Game.UiEvent.NavigateToResults)
            }
        } else {
            _uiState.update {
                it.copy(
                    heartNumber = newHeartNumber,
                    answer = "",
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
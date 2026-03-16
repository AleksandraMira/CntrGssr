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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val countryDao: CountryDao,
    private val resourceResolver: ResourceResolver,
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(Game.UiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<Game.UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val country = preferencesDataStoreRepository.countryId
        .distinctUntilChanged()
        .map { id ->
            setInitialValues()
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
            Game.UserEvent.OnSubmitAnswer -> onSubmitAnswer()
            Game.UserEvent.OnSnackbarShown -> clearSnackbar()
            Game.UserEvent.OnGiveUpDialogConfirm -> giveUp()
            Game.UserEvent.OnGiveUpButtonClicked -> _uiState.update {
                it.copy(isGiveUpDialogVisible = true)
            }
            Game.UserEvent.OnGiveUpDialogDismiss -> _uiState.update {
                it.copy(isGiveUpDialogVisible = false)
            }
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

        viewModelScope.launch {
            if (answer.equals(correctCountryName, ignoreCase = true)) {
                _uiEvent.emit(Game.UiEvent.NavigateToResults)
                return@launch
            }

            val newHeartNumber = currentHeartNumber - 1
            preferencesDataStoreRepository.setHeartNumber(newHeartNumber)
            if (newHeartNumber == 0) {
                _uiEvent.emit(Game.UiEvent.NavigateToResults)
            } else {
                _uiState.update {
                    it.copy(
                        heartNumber = newHeartNumber,
                        answer = "",
                    )
                }
            }
        }
    }

    private fun clearSnackbar() {
        _uiState.update {
            it.copy(snackbarMessage = null)
        }
    }

    private fun giveUp() {
        viewModelScope.launch {
            preferencesDataStoreRepository.setIsGaveUp(true)
            _uiEvent.emit(Game.UiEvent.NavigateToResults)
        }
    }

    private suspend fun setInitialValues() {
        preferencesDataStoreRepository.setIsGaveUp(false)
        preferencesDataStoreRepository.setHeartNumber(3)
    }
}
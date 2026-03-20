package com.example.cntrgssr.presentation.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cntrgssr.core.data.dao.CountryDao
import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val countryDao: CountryDao,
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository,
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<Results.UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val uiState = combine(
        preferencesDataStoreRepository.countryId,
        preferencesDataStoreRepository.heartNumber,
        preferencesDataStoreRepository.isGaveUp,
        preferencesDataStoreRepository.hintNumber,
    ) { id, heartNumber, isGaveUp, hintNumber ->
        val hintPoints = -hintNumber * 10
        val heartPoints = -(3 - heartNumber) * 10
        val totalPoints = 100 + heartPoints + hintPoints
        Results.UiState(
            countryName = countryDao.getCountryNameById(id),
            heartNumber = heartNumber,
            heartPoints = heartPoints,
            isGaveUp = isGaveUp,
            hintNumber = hintNumber,
            hintPoints = hintPoints,
            points = totalPoints,
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Results.UiState(),
        )

    fun onUserEvent(event: Results.UserEvent) {
        when (event) {
            Results.UserEvent.OnExitButtonClicked -> viewModelScope.launch {
                _uiEvent.emit(Results.UiEvent.NavigateToHome)
            }

            Results.UserEvent.OnPlayAgainButtonClicked -> startNewGame()
        }
    }

    private fun startNewGame() = viewModelScope.launch {
        preferencesDataStoreRepository.setCountryId(
            countryDao.getRandomCountryId()
        )

        _uiEvent.emit(Results.UiEvent.NavigateToGame)
    }
}
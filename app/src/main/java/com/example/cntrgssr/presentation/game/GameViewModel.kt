package com.example.cntrgssr.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cntrgssr.R
import com.example.cntrgssr.core.data.api.CountryApi
import com.example.cntrgssr.core.data.dao.CountryDao
import com.example.cntrgssr.core.data.enums.HintType
import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepository
import com.example.cntrgssr.core.util.ResourceResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val countryApi: CountryApi,
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
        .onEach { setInitialValues() }
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
            Game.UserEvent.OnSubmitAnswer -> onSubmitAnswer()
            Game.UserEvent.OnSnackbarShown -> clearSnackbar()
            Game.UserEvent.OnGiveUpDialogConfirm -> giveUp()
            Game.UserEvent.OnGiveUpButtonClicked -> _uiState.update {
                it.copy(isGiveUpDialogVisible = true)
            }
            Game.UserEvent.OnGiveUpDialogDismiss -> _uiState.update {
                it.copy(isGiveUpDialogVisible = false)
            }
            is Game.UserEvent.OnHintOptionSelected -> _uiState.update {
                it.copy(selectedHint = event.hintType)
            }
            Game.UserEvent.OnHintButtonClicked -> handleHintButtonClick()
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
            showSnackbar(resourceResolver.getString(R.string.game_screen_empty_answer))
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

    private fun showSnackbar(message: String) {
        _uiState.update { it.copy(snackbarMessage = message) }
    }

    private fun clearSnackbar() {
        _uiState.update {
            it.copy(snackbarMessage = null)
        }
    }

    private fun giveUp() {
        _uiState.update {
            it.copy(isGiveUpDialogVisible = false)
        }
        viewModelScope.launch {
            preferencesDataStoreRepository.setIsGaveUp(true)
            _uiEvent.emit(Game.UiEvent.NavigateToResults)
        }
    }

    private suspend fun setInitialValues() {
        preferencesDataStoreRepository.setIsGaveUp(false)
        preferencesDataStoreRepository.setHeartNumber(INITIAL_HEARTS)
        preferencesDataStoreRepository.setHintNumber(INITIAL_HINTS)
    }

    private fun handleHintButtonClick() {
        if (uiState.value.selectedHint == null) {
            showSnackbar(resourceResolver.getString(R.string.game_screen_no_hint_selected))
            return
        }
        val selectedHint = uiState.value.selectedHint ?: return


        viewModelScope.launch {
            preferencesDataStoreRepository.setHintNumber(preferencesDataStoreRepository.hintNumber.first() + 1)
            _uiState.update {
                it.copy(
                    availableHintOptions = it.availableHintOptions - selectedHint,
                    selectedHint = null,
                    hintLog = it.hintLog + (selectedHint to (selectedHint.getHint() ?: resourceResolver.getString(R.string.game_screen_error)))
                )
            }
        }
    }

    private suspend fun HintType.getHint() = when (this) {
        HintType.CONTINENT -> country.value?.continent?.resId?.let { resourceResolver.getString(it) }
        HintType.CAPITAL_LETTERS -> country.value?.capital?.filter { it.isLetter() }?.length.toString()
        HintType.POPULATION -> getPopulation()
    }

    private suspend fun getPopulation(): String? {
        _uiState.update { it.copy(isLoading = true) }
        val name = country.value?.name ?: return null

        val result = try {
            val response = countryApi.getCountry(name)
            val population = response.firstOrNull()?.population?: return null

            Timber.d("Population: $population")
            resourceResolver.getString(R.string.game_screen_population_number, population)
        } catch (e: Exception) {
            Timber.e(e, "API error")
            null
        } finally {
            _uiState.update { it.copy(isLoading = false) }
        }

        return result
    }

    private companion object {
        const val INITIAL_HEARTS = 3
        const val INITIAL_HINTS = 0
    }
}
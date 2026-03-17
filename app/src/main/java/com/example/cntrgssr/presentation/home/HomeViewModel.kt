package com.example.cntrgssr.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cntrgssr.core.data.dao.CountryDao
import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepository
import com.example.cntrgssr.createCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val countryDao: CountryDao,
    private val preferencesDataStoreRepository: PreferencesDataStoreRepository,
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<Home.UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

//    init {
//        viewModelScope.launch {
//            countryDao.deleteAllCountries()
//            countryDao.insertCountries(
//                createCountries().map { it.toEntity() }
//            )
//        }
//    }

    fun onUserEvent(event: Home.UserEvent) {
        when (event) {
            Home.UserEvent.OnGuideButtonClicked -> viewModelScope.launch {
                _uiEvent.emit(Home.UiEvent.NavigateToGuide)
            }
            Home.UserEvent.OnStartButtonClicked -> startTheGame()
        }
    }

    private fun startTheGame() {
        viewModelScope.launch {
            val countryId = countryDao.getRandomCountryId()

            Timber.d("Selected country ID: $countryId")
            preferencesDataStoreRepository.setCountryId(countryId)

            Timber.d("Emitting NavigateToGame event")
            _uiEvent.emit(Home.UiEvent.NavigateToGame)
        }
    }
}
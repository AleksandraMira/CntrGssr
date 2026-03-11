package com.example.cntrgssr.presentation.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cntrgssr.core.data.dao.CountryDao
import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val countryDao: CountryDao,
    preferencesDataStoreRepository: PreferencesDataStoreRepository,
) : ViewModel() {
    val uiState = preferencesDataStoreRepository.countryId
        .map {
            Results.UiState(
                countryName = countryDao.getCountryNameById(it)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Results.UiState(),
        )
}
package com.example.cntrgssr.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiEvent = MutableSharedFlow<Home.UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onUserEvent(event: Home.UserEvent) {
        when (event) {
            Home.UserEvent.OnGuideButtonClicked -> viewModelScope.launch {
                _uiEvent.emit(Home.UiEvent.NavigateToGuide)
            }
            Home.UserEvent.OnStartButtonClicked -> viewModelScope.launch {
                _uiEvent.emit(Home.UiEvent.NavigateToGame)
            }
        }
    }
}
package com.example.cntrgssr.presentation.guide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuideViewModel @Inject constructor() : ViewModel() {
    private val _uiEvent = MutableSharedFlow<Guide.UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onUserEvent(event: Guide.UserEvent) {
        when (event) {
            is Guide.UserEvent.OnBackClicked -> viewModelScope.launch {
                _uiEvent.emit(Guide.UiEvent.NavigateToHome)
            }
        }
    }
}
package com.example.cntrgssr.presentation.results

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.cntrgssr.core.navigation.NavigationNode

object Results : NavigationNode() {
    @Composable
    override fun Screen(navController: NavHostController) {
        val viewModel: ResultsViewModel = hiltViewModel()
        ResultsScreen(
            uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        )
    }

    data class UiState(
        val countryName: String = "",
    )
}
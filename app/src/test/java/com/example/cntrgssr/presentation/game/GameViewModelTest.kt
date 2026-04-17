package com.example.cntrgssr.presentation.game

import com.example.cntrgssr.FakePreferenceDataStoreRepository
import com.example.cntrgssr.core.data.api.CountryApi
import com.example.cntrgssr.core.data.dao.CountryDao
import com.example.cntrgssr.core.data.entity.CountryEntity
import com.example.cntrgssr.core.data.enums.Continent
import com.example.cntrgssr.core.util.ResourceResolver
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {
    private val countryApi = mockk<CountryApi>()
    private val countryDao = mockk<CountryDao>()
    private val resourceResolver = mockk<ResourceResolver>()
    private val preferencesDataStoreRepository = FakePreferenceDataStoreRepository()

    private lateinit var viewModel: GameViewModel

    @BeforeEach
    fun setUp() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        coEvery { countryDao.getCountryById(any()) } returns CountryEntity(
            id = 1L,
            name = "Poland",
            capital = "Warsaw",
            continent = Continent.EUROPE,
        )

        viewModel = GameViewModel(
            countryApi = countryApi,
            countryDao = countryDao,
            resourceResolver = resourceResolver,
            preferencesDataStoreRepository = preferencesDataStoreRepository,
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `onSubmitAnswer decreases heart on wrong answer`() {
        val uiStateBefore = viewModel.uiState.value
        viewModel.onEvent(Game.UserEvent.OnAnswerChange("Germany"))
        viewModel.onEvent(Game.UserEvent.OnSubmitAnswer)

        Assertions.assertEquals(
            viewModel.uiState.value, uiStateBefore.copy(
                heartNumber = uiStateBefore.heartNumber - 1,
            )
        )
    }

    @Test
    fun `onSubmitAnswer shows snackbar on empty answer`() = runTest {
        every { resourceResolver.getString(any()) } returns "Error message"
        val uiStateBefore = viewModel.uiState.value

        viewModel.onEvent(Game.UserEvent.OnAnswerChange(""))
        viewModel.onEvent(Game.UserEvent.OnSubmitAnswer)

        Assertions.assertEquals(
            viewModel.uiState.value, uiStateBefore.copy(
                snackbarMessage = "Error message",
            )
        )
    }

    @Test
    fun `handleHintButtonClick shows snackbar if no hint selected`() = runTest {
        every { resourceResolver.getString(any()) } returns "Hint message"
        val uiStateBefore = viewModel.uiState.value

        viewModel.onEvent(Game.UserEvent.OnHintButtonClicked)

        Assertions.assertEquals(
            viewModel.uiState.value, uiStateBefore.copy(
                snackbarMessage = "Hint message",
            )
        )
    }
}
package com.example.cntrgssr

import com.example.cntrgssr.core.dataStore.PreferencesDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakePreferenceDataStoreRepository : PreferencesDataStoreRepository {
    private companion object {
        const val DEFAULT_COUNTRY_ID = 1L
    }

    override val countryId = MutableStateFlow(DEFAULT_COUNTRY_ID)
    override val heartNumber = MutableStateFlow(3)
    override val isGaveUp = MutableStateFlow(false)
    override val hintNumber = MutableStateFlow(0)

    override suspend fun setCountryId(countryId: Long) {
        this.countryId.update { countryId }
    }

    override suspend fun setHeartNumber(heartNumber: Int) {
        this.heartNumber.update { heartNumber }
    }

    override suspend fun setIsGaveUp(isGaveUp: Boolean) {
        this.isGaveUp.update { isGaveUp }
    }

    override suspend fun setHintNumber(hintNumber: Int) {
        this.hintNumber.update { hintNumber }
    }
}
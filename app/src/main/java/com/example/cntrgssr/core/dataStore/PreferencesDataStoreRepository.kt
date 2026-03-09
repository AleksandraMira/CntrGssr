package com.example.cntrgssr.core.dataStore

import kotlinx.coroutines.flow.Flow

interface PreferencesDataStoreRepository {
    val countryId: Flow<Long>

    suspend fun setCountryId(countryId: Long)
}
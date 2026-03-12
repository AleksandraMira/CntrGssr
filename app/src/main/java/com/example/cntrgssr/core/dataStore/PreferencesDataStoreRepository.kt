package com.example.cntrgssr.core.dataStore

import kotlinx.coroutines.flow.Flow

interface PreferencesDataStoreRepository {
    val countryId: Flow<Long>
    val heartNumber: Flow<Int>

    suspend fun setCountryId(countryId: Long)
    suspend fun setHeartNumber(heartNumber: Int)
}
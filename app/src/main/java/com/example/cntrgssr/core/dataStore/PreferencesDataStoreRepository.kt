package com.example.cntrgssr.core.dataStore

import kotlinx.coroutines.flow.Flow

interface PreferencesDataStoreRepository {
    val countryId: Flow<Long>
    val heartNumber: Flow<Int>
    val isGaveUp: Flow<Boolean>
    val hintNumber: Flow<Int>

    suspend fun setCountryId(countryId: Long)
    suspend fun setHeartNumber(heartNumber: Int)
    suspend fun setIsGaveUp(isGaveUp: Boolean)
    suspend fun setHintNumber(hintNumber: Int)
}
package com.example.cntrgssr.core.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_preferences")

class PreferencesDataStoreRepositoryImpl @Inject constructor(
    private val preferences: DataStore<Preferences>,
) : PreferencesDataStoreRepository {
    companion object {
        val COUNTRY_ID_KEY = longPreferencesKey("country_id")
        val HEART_NUMBER_KEY = intPreferencesKey("heart_number")
        val IS_GAVE_UP_KEY = booleanPreferencesKey("is_gave_up")
        val HINT_NUMBER_KEY = intPreferencesKey("hint_number")
    }

    override val countryId: Flow<Long> = preferences.data
        .catch { emit(emptyPreferences()) }
        .map { it[COUNTRY_ID_KEY] ?: -1L }

    override val heartNumber: Flow<Int> = preferences.data
        .catch { emit(emptyPreferences()) }
        .map { it[HEART_NUMBER_KEY] ?: 0 }

    override val isGaveUp: Flow<Boolean> = preferences.data
        .catch { emit(emptyPreferences()) }
        .map { it[IS_GAVE_UP_KEY] ?: false }

    override val hintNumber: Flow<Int> = preferences.data
        .catch { emit(emptyPreferences()) }
        .map { it[HINT_NUMBER_KEY] ?: 0 }

    override suspend fun setCountryId(countryId: Long) {
        preferences.edit { it[COUNTRY_ID_KEY] = countryId }
    }

    override suspend fun setHeartNumber(heartNumber: Int) {
        preferences.edit { it[HEART_NUMBER_KEY] = heartNumber }
    }

    override suspend fun setIsGaveUp(isGaveUp: Boolean) {
        preferences.edit { it[IS_GAVE_UP_KEY] = isGaveUp }
    }

    override suspend fun setHintNumber(hintNumber: Int) {
        preferences.edit { it[HINT_NUMBER_KEY] = hintNumber }
    }
}
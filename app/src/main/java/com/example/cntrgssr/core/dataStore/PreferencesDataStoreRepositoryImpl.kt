package com.example.cntrgssr.core.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
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
    }

    override val countryId: Flow<Long> = preferences.data
        .catch { emit(emptyPreferences()) }
        .map { it[COUNTRY_ID_KEY] ?: -1L }

    override suspend fun setCountryId(countryId: Long) {
        preferences.edit { it[COUNTRY_ID_KEY] = countryId }
    }
}
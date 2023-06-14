package com.adityafakhri.medfluffy.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferences private constructor(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getDarkMode(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveDarkMode(darkModeState: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = darkModeState
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: AppPreferences? = null
        fun getInstance(dataStore: DataStore<androidx.datastore.preferences.core.Preferences>): AppPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AppPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
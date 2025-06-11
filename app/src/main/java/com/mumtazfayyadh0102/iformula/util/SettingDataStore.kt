package com.mumtazfayyadh0102.iformula.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Inisialisasi DataStore
private val Context.dataStore by preferencesDataStore(name = "settings_config")

class SettingsDataStore(private val context: Context) {

    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val THEME_COLOR = stringPreferencesKey("theme_color")
    }

    // Flow untuk preferensi layout
    val layoutFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[IS_LIST] ?: true }

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }

    // Flow untuk preferensi warna tema
    val themeColorFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[THEME_COLOR] ?: "red" }

    suspend fun saveThemeColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_COLOR] = color
        }
    }
}

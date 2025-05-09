package com.mumtazfayyadh0102.iformula.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mumtazfayyadh0102.iformula.database.RaceNotesDb
import com.mumtazfayyadh0102.iformula.util.SettingsDataStore

class ViewModelFactory(
    private val context: Context,
    private val settingsDataStore: SettingsDataStore? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RaceNotesViewModel::class.java) -> {
                val dao = RaceNotesDb.getInstance(context).raceNotesDao()
                RaceNotesViewModel(dao) as T
            }
            modelClass.isAssignableFrom(ThemeViewModel::class.java) -> {
                requireNotNull(settingsDataStore) { "SettingsDataStore is required for ThemeViewModel" }
                ThemeViewModel(settingsDataStore) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

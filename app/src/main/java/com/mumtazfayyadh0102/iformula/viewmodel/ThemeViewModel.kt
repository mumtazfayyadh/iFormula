package com.mumtazfayyadh0102.iformula.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumtazfayyadh0102.iformula.util.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val dataStore: SettingsDataStore) : ViewModel() {
    val selectedColor: StateFlow<String> = dataStore.themeColorFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "red")

    fun updateColor(color: String) {
        viewModelScope.launch {
            dataStore.saveThemeColor(color)
        }
    }
}

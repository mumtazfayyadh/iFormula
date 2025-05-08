package com.mumtazfayyadh0102.iformula.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mumtazfayyadh0102.iformula.database.RaceNotesDb
import com.mumtazfayyadh0102.iformula.ui.screen.RaceNotesViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = RaceNotesDb.getInstance(context).raceNotesDao()
        if (modelClass.isAssignableFrom(RaceNotesViewModel::class.java)) {
            return RaceNotesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

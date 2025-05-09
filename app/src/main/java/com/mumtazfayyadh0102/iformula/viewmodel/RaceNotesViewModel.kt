package com.mumtazfayyadh0102.iformula.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumtazfayyadh0102.iformula.database.RaceNotesDao
import com.mumtazfayyadh0102.iformula.model.RaceNotes
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RaceNotesViewModel(private val dao: RaceNotesDao) : ViewModel() {

    val raceNotes: StateFlow<List<RaceNotes>> = dao.getAll()
        .map { it.sortedByDescending { note -> note.points } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insert(note: RaceNotes) {
        viewModelScope.launch {
            dao.insert(note)
        }
    }

    fun update(note: RaceNotes) {
        viewModelScope.launch {
            dao.update(note)
        }
    }

    fun delete(note: RaceNotes) {
        viewModelScope.launch {
            dao.delete(note)
        }
    }

    suspend fun getNoteById(id: Int): RaceNotes? {
        return dao.getById(id)
    }
}

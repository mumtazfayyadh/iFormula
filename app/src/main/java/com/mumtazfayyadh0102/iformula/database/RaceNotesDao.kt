package com.mumtazfayyadh0102.iformula.database

import androidx.room.*
import com.mumtazfayyadh0102.iformula.model.RaceNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceNotesDao {
    @Query("SELECT * FROM RaceNotes")
    fun getAll(): Flow<List<RaceNotes>>

    @Query("SELECT * FROM RaceNotes WHERE id = :id")
    suspend fun getById(id: Int): RaceNotes?

    @Insert
    suspend fun insert(note: RaceNotes)

    @Update
    suspend fun update(note: RaceNotes)

    @Delete
    suspend fun delete(note: RaceNotes)
}

package com.mumtazfayyadh0102.iformula.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RaceNotes (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val driverName: String,
    val bestLapTime: String,
    val startPosition: Int,
    val points: Int
)   {
}
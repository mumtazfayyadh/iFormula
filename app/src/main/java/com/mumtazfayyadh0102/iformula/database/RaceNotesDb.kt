package com.mumtazfayyadh0102.iformula.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mumtazfayyadh0102.iformula.model.RaceNotes

@Database(entities = [RaceNotes::class], version = 1)
abstract class RaceNotesDb : RoomDatabase() {
    abstract fun raceNotesDao(): RaceNotesDao

    companion object {
        @Volatile private var INSTANCE: RaceNotesDb? = null

        fun getInstance(context: Context): RaceNotesDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RaceNotesDb::class.java,
                    "race_notes.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

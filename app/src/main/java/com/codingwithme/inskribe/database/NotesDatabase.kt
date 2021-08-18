package com.codingwithme.inskribe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codingwithme.inskribe.dao.NoteDao
import com.codingwithme.inskribe.entities.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    companion object {
        var notesDatabase: NotesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NotesDatabase {
            if (notesDatabase == null) {
                notesDatabase = Room.databaseBuilder(
                    context
                    , NotesDatabase::class.java
                    , "notes.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return notesDatabase!!
        }
    }

    abstract fun noteDao():NoteDao
}
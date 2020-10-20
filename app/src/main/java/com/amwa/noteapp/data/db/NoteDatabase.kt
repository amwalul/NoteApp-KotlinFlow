package com.amwa.noteapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amwa.noteapp.data.db.dao.NoteDao
import com.amwa.noteapp.data.db.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DB_NAME = "note_database"

        fun create(context: Context): NoteDatabase = Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            DB_NAME
        ).build()
    }
}
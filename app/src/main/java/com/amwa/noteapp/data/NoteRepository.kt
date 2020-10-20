package com.amwa.noteapp.data

import com.amwa.noteapp.data.db.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun addNote(note: Note)

    suspend fun getNote(id: Int): Note

    suspend fun deleteNote(note: Note)
}
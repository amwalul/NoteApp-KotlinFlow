package com.amwa.noteapp.data

import com.amwa.noteapp.data.db.dao.NoteDao
import com.amwa.noteapp.data.db.model.Note
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAll()

    override suspend fun addNote(note: Note) = withContext(IO) { noteDao.insert(note)
    }

    override suspend fun getNote(id: Int): Note = withContext(IO) { noteDao.get(id)
    }

    override suspend fun deleteNote(note: Note) = withContext(IO) { noteDao.delete(note)
    }
}
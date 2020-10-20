package com.amwa.noteapp.ui.note.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amwa.noteapp.data.db.model.Note
import com.amwa.noteapp.data.NoteRepository
import kotlinx.coroutines.launch

class NoteListViewModel @ViewModelInject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val notes: LiveData<List<Note>> = noteRepository
        .getAllNotes()
        .asLiveData()

    fun deleteNote(note: Note) = viewModelScope.launch { noteRepository.deleteNote(note) }

    fun restoreNote(note: Note) = viewModelScope.launch { noteRepository.addNote(note) }
}
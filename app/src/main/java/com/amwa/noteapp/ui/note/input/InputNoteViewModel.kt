package com.amwa.noteapp.ui.note.input

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amwa.noteapp.data.db.model.Note
import com.amwa.noteapp.data.NoteRepository
import kotlinx.coroutines.launch

class InputNoteViewModel @ViewModelInject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    fun addNote(note: Note) = viewModelScope.launch { noteRepository.addNote(note) }
}
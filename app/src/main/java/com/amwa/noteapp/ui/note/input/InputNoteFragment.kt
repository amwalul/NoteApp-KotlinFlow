package com.amwa.noteapp.ui.note.input

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.amwa.noteapp.R
import com.amwa.noteapp.data.db.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_input_note.*

@AndroidEntryPoint
class InputNoteFragment : Fragment(R.layout.fragment_input_note) {

    private val viewModel by viewModels<InputNoteViewModel>()
    private val args by navArgs<InputNoteFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInputView()
    }

    private fun initInputView() {
        etTitle.setText(args.note?.title)
        etNote.setText(args.note?.note)
    }

    private fun addTextWatcher(menuItem: MenuItem) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                menuItem.isEnabled =
                    (etTitle.text.toString() != args.note?.title || etNote.text.toString() != args.note?.note)
                            && (!etTitle.text.isNullOrBlank() && !etNote.text.isNullOrBlank())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }

        etTitle.addTextChangedListener(textWatcher)
        etNote.addTextChangedListener(textWatcher)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        initInputMenu(menu, inflater)
    }

    private fun initInputMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_input, menu)

        val menuAdd = menu.findItem(R.id.menuAdd)
        menuAdd.isEnabled = false
        addTextWatcher(menuAdd)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuAdd -> {
            addNote()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun addNote() {
        val note = Note(
            args.note?.id,
            etTitle.text.toString(),
            etNote.text.toString()
        )
        viewModel.addNote(note)

        etTitle.clearFocus()
        etNote.clearFocus()

        findNavController().popBackStack()
    }
}
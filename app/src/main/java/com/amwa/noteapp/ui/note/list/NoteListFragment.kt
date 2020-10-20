package com.amwa.noteapp.ui.note.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amwa.noteapp.R
import com.amwa.noteapp.data.db.model.Note
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_list.*

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    private val viewModel by viewModels<NoteListViewModel>()

    private val noteAdapter by lazy {
        NoteAdapter(object : NoteAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Note) {
                val direction =
                    NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment(item)
                findNavController().navigate(direction)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListView()
        initAddView()
        initObservers()
    }

    private fun initListView() {
        with(rvNote) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }

        addSwipeToDelete()
        scrollTopAfterInsert()
    }

    private fun addSwipeToDelete() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedNotePosition = viewHolder.adapterPosition
                val swipedNote = noteAdapter.getItem(swipedNotePosition)

                deleteNote(swipedNote)
                showDeleteSnackbar(swipedNote)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvNote)
    }

    private fun showDeleteSnackbar(note: Note) {
        val snackbar =
            Snackbar.make(requireView(), "Item was removed from the list.", Snackbar.LENGTH_LONG)

        snackbar.setAction("UNDO") {
            restoreNote(note)
        }

        snackbar.show()
    }

    private fun deleteNote(note: Note) {
        viewModel.deleteNote(note)
    }

    private fun restoreNote(note: Note) {
        viewModel.restoreNote(note)
    }

    private fun scrollTopAfterInsert() {
        noteAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                rvNote.smoothScrollToPosition(0)
            }
        })
    }

    private fun initAddView() {
        fabAdd.setOnClickListener {
            val direction = NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initObservers() {
        viewModel.notes.observe(viewLifecycleOwner, {
            noteAdapter.submitList(it)
        })
    }
}
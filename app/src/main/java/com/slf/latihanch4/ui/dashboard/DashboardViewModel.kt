// DashboardViewModel.kt
package com.slf.latihanch4.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slf.latihanch4.data.model.Note

class DashboardViewModel : ViewModel() {

    // Create LiveData to hold the list of notes
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    // Dummy list of notes (you should replace this with your actual data source)
    private val notesList = mutableListOf<Note>()

    // Initialize ViewModel by loading notes
    init {
        loadNotes()
    }

    // Method to load notes
    private fun loadNotes() {
        // Here you should fetch your notes from your data source
        // For example, you can add some dummy data like this:
        notesList.add(Note(1, "title 1", "Content 1"))
        notesList.add(Note(2, "title 2", "Content 2"))
        notesList.add(Note(3, "title 3", "Content 3"))

        // Update LiveData with the new list of notes
        _notes.value = notesList
    }

}

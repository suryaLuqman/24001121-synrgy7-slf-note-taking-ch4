// DashboardViewModel.kt
package com.slf.latihanch4.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slf.latihanch4.data.model.Note

class DashboardViewModel : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    init {
        loadNotes()
    }

    private fun loadNotes() {
        // Replace this with your actual logic to fetch notes
        val dummyNotes = mutableListOf<Note>()
        dummyNotes.add(Note(1, "title 1", "Content 1"))
        dummyNotes.add(Note(2, "title 2", "Content 2"))
        dummyNotes.add(Note(3, "title 3", "Content 3"))
        _notes.value = dummyNotes
    }
}

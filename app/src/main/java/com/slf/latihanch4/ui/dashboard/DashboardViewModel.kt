package com.slf.latihanch4.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.data.room.appDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val db = appDb.getInstance(application).noteDao()

    val notes: LiveData<List<Note>> = db.getAll()

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            db.insert(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            db.update(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            db.delete(note)
        }
    }
}

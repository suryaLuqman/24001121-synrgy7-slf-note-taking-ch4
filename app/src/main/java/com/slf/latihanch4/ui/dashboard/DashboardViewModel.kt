package com.slf.latihanch4.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.data.room.appDb // Ubah ini

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        appDb::class.java, "database-name" // Ubah ini
    ).build()

    val notes = db.noteDao().getAll()

    suspend fun insert(note: Note) = withContext(Dispatchers.IO) {
        db.noteDao().insert(note)
    }

    suspend fun update(note: Note) = withContext(Dispatchers.IO) {
        db.noteDao().update(note)
    }

    suspend fun delete(note: Note) = withContext(Dispatchers.IO) {
        db.noteDao().delete(note)
    }
}

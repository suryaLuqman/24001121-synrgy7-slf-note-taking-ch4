package com.slf.latihanch4.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.slf.latihanch4.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}

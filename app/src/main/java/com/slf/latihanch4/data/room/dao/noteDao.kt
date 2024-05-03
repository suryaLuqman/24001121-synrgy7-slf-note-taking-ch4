package com.slf.latihanch4.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.slf.latihanch4.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAll(): LiveData<List<Note>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update (onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}

package com.slf.latihanch4.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.data.room.dao.NoteDao

@Database(entities = [Note::class], version = 1)
abstract class appDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}

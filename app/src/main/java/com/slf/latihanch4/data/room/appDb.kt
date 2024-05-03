package com.slf.latihanch4.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.data.room.dao.NoteDao

@Database(entities = [Note::class], version = 1)
abstract class appDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: appDb? = null

        fun getInstance(context: Context): appDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDb::class.java,
                    "notes-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

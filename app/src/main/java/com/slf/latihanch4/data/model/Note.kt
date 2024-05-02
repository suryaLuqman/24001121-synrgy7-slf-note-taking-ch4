package com.slf.latihanch4.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String
)


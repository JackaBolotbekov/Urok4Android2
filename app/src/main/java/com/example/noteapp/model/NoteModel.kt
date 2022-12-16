package com.example.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel (
    var title: String,
    var description: String,
    var date: String,
) : java.io.Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

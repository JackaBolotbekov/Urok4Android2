package com.example.noteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.data.db.daos.NoteDao
import com.example.noteapp.model.NoteModel

@Database(entities = [NoteModel::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao
}
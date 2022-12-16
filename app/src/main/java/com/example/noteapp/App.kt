package com.example.noteapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.noteapp.data.db.AppDatabase
import com.example.noteapp.utils.PreferenceHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        getInstance()
        PreferenceHelper.unit(this)
    }

    companion object {
        private var appDatabase: AppDatabase? = null
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

         fun getInstance(): AppDatabase? {
            if (appDatabase == null) {
                appDatabase = context?.let {
                    Room.databaseBuilder(
                        it, AppDatabase::class.java, "note.database"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }
            }
            return appDatabase
        }
    }
}
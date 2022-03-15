package com.mariomanzano.nasa_explorer

import android.app.Application
import androidx.room.Room
import com.mariomanzano.nasa_explorer.data.database.NasaDatabase

class App : Application() {

    lateinit var db: NasaDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            NasaDatabase::class.java, "nasa-db"
        ).build()
    }
}
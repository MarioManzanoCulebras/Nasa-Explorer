package com.mariomanzano.nasa_explorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbPOD::class], version = 1, exportSchema = false)
abstract class NasaDatabase : RoomDatabase() {

    abstract fun movieDao(): NasaDao
}
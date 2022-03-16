package com.mariomanzano.nasa_explorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [DbPOD::class, DbEarth::class, DbMars::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NasaDatabase : RoomDatabase() {

    abstract fun nasaDao(): NasaDao
}
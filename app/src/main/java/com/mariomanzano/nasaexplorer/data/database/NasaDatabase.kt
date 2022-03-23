package com.mariomanzano.nasaexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [DbPODLastUpdate::class, DbEarthLastUpdate::class, DbMarsLastUpdate::class, DbPOD::class, DbEarth::class, DbMars::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NasaDatabase : RoomDatabase() {

    abstract fun nasaDao(): NasaDao
}
package com.mariomanzano.nasaexplorer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DbEarthLastUpdate(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Calendar
)

package com.mariomanzano.nasa_explorer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DbEarth(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Calendar,
    val title: String,
    val description: String,
    val url: String,
    val favorite: Boolean
)
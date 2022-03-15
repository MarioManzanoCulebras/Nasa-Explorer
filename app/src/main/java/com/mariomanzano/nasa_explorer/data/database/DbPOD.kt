package com.mariomanzano.nasa_explorer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbPOD(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Long,
    val title: String,
    val description: String,
    val url: String,
    val copyRight: String,
    val favorite: Boolean
)
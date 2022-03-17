package com.mariomanzano.nasaexplorer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DbPODFavorite(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Calendar,
    val title: String,
    val description: String,
    val url: String,
    val copyRight: String
)
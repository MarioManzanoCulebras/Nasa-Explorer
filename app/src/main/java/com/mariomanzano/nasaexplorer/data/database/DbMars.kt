package com.mariomanzano.nasaexplorer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DbMars(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Calendar,
    val title: String,
    val description: String,
    val url: String,
    val sun: Int,
    val cameraName: String?,
    val roverName: String?,
    val roverLandingDate: Calendar,
    val roverLaunchingDate: Calendar,
    val roverMissionStatus: String?,
    val favorite: Boolean,
    val lastRequest: Calendar
)
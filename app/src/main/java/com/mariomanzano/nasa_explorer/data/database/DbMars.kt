package com.mariomanzano.nasa_explorer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbMars(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Long,
    val title: String,
    val description: String,
    val url: String,
    val sun: Int,
    val cameraName: String?,
    val roverName: String?,
    val roverLandingDate: Long?,
    val roverLaunchingDate: Long?,
    val roverMissionStatus: String?,
    val favorite: Boolean,
)
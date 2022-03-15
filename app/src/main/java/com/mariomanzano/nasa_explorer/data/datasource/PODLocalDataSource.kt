package com.mariomanzano.nasa_explorer.data.datasource

import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import kotlinx.coroutines.flow.Flow

interface PODLocalDataSource {
    val podList: Flow<List<PictureOfDayItem>>

    suspend fun isPODListEmpty(): Boolean
    fun findPODById(id: Int): Flow<PictureOfDayItem>
    suspend fun savePODList(items: List<PictureOfDayItem>): Error?
}
package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.datasource.PODLocalDataSource
import com.mariomanzano.nasa_explorer.data.datasource.PODRemoteDataSource
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import kotlinx.coroutines.flow.Flow

class DailyPicturesRepository(
    private val localDataSource: PODLocalDataSource,
    private val remoteDataSource: PODRemoteDataSource
) {

    val podList = localDataSource.podList

    fun findById(id: Int): Flow<PictureOfDayItem> = localDataSource.findPODById(id)

    suspend fun requestPODList(): Error? {
        if (localDataSource.isPODListEmpty()) {
            val items = remoteDataSource.findPODitems()
            items.fold(ifLeft = { return it }) {
                localDataSource.savePODList(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(pictureOfDayItem: PictureOfDayItem): Error? {
        val updatedPOD = pictureOfDayItem.copy(favorite = !pictureOfDayItem.favorite)
        return localDataSource.savePODList(listOf(updatedPOD))
    }
}
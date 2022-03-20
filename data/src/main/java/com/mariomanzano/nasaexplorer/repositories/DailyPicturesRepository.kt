package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODRemoteDataSource
import kotlinx.coroutines.flow.Flow

class DailyPicturesRepository(
    private val localDataSource: PODLocalDataSource,
    private val remoteDataSource: PODRemoteDataSource
) {

    val podList = localDataSource.podList

    fun findById(id: Int): Flow<PictureOfDayItem> = localDataSource.findPODById(id)

    suspend fun requestPODList(dayChanged: Boolean): Error? {
        if (dayChanged)
            localDataSource.savePODList(emptyList())
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
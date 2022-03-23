package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DailyPicturesRepository(
    private val localDataSource: PODLocalDataSource,
    private val remoteDataSource: PODRemoteDataSource
) {

    val podList = localDataSource.podList

    fun findById(id: Int): Flow<PictureOfDayItem> = localDataSource.findPODById(id)

    suspend fun requestPODList(): Error? {
        val items = remoteDataSource.findPODitems()
        items.fold(ifLeft = { return it }) { serverList ->
            podList.first { true }.also { list ->
                val favourites = list.filter { it.favorite }
                serverList.map { pod ->
                    val element = favourites.find {
                        it.date == pod.date &&
                                it.title == pod.title &&
                                it.description == pod.description &&
                                it.url == pod.url &&
                                it.type == pod.type
                    }
                    if (element != null) {
                        pod.id = element.id
                        pod.favorite = true
                    }
                }
                localDataSource.savePODList(serverList)
            }
        }
        return null
    }

    suspend fun resetPODList(): Error? {
        localDataSource.clearPODList()
        return null
    }

    suspend fun switchFavorite(pictureOfDayItem: PictureOfDayItem): Error? {
        val updatedPOD = pictureOfDayItem.copy(favorite = !pictureOfDayItem.favorite)
        return localDataSource.savePODList(listOf(updatedPOD))
    }
}
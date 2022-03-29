package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODRemoteDataSource
import com.mariomanzano.nasaexplorer.sameDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.*

class DailyPicturesRepository(
    private val localDataSource: PODLocalDataSource,
    private val remoteDataSource: PODRemoteDataSource
) {

    val podList = localDataSource.podList

    fun findById(id: Int): Flow<PictureOfDayItem> = localDataSource.findPODById(id)

    suspend fun requestPODSingleDay(date: Calendar): Error? {
        podList.first { true }.also { localList ->
            val find = localList.find { it.date.sameDay(date) }
            if (find == null) {
                remoteDataSource.findPODDay(date).fold(ifLeft = { return it }) { pod ->
                    localDataSource.savePODList(listOf(pod))
                }
            }
        }
        return null
    }

    suspend fun requestPODList(): Error? {
        val items = remoteDataSource.findPODitems()
        items.fold(ifLeft = { return it }) { serverList ->
            localDataSource.savePODList(serverList)
        }
        return null
    }

    suspend fun switchFavorite(pictureOfDayItem: PictureOfDayItem): Error? {
        val updatedPOD = pictureOfDayItem.copy(favorite = !pictureOfDayItem.favorite)
        return localDataSource.savePODList(listOf(updatedPOD))
    }
}
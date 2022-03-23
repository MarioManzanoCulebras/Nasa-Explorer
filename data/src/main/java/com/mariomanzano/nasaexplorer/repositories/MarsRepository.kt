package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MarsRepository(
    private val localDataSource: MarsLocalDataSource,
    private val remoteDataSource: MarsRemoteDataSource
) {

    val marsList = localDataSource.marsList

    fun findById(id: Int): Flow<MarsItem> = localDataSource.findMarsById(id)

    suspend fun requestMarsList(): Error? {
        val items = remoteDataSource.findMarsItems()
        items.fold(ifLeft = { return it }) { serverList ->
            marsList.first { true }.also { list ->
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
                localDataSource.saveMarsList(serverList)
            }
        }
        return null
    }

    suspend fun resetMarsList(): Error? {
        localDataSource.clearMarsList()
        return null
    }

    suspend fun switchFavorite(marsItem: MarsItem): Error? {
        val updated = marsItem.copy(favorite = !marsItem.favorite)
        return localDataSource.saveMarsList(listOf(updated))
    }
}
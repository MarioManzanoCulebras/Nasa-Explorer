package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.datasource.MarsLocalDataSource
import com.mariomanzano.nasa_explorer.data.datasource.MarsRemoteDataSource
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import kotlinx.coroutines.flow.Flow

class MarsRepository(
    private val localDataSource: MarsLocalDataSource,
    private val remoteDataSource: MarsRemoteDataSource
) {

    val marsList = localDataSource.marsList

    fun findById(id: Int): Flow<MarsItem> = localDataSource.findMarsById(id)

    suspend fun requestMarsList(): Error? {
        if (localDataSource.isMarsListEmpty()) {
            val items = remoteDataSource.findMarsItems()
            items.fold(ifLeft = { return it }) {
                localDataSource.saveMarsList(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(marsItem: MarsItem): Error? {
        val updated = marsItem.copy(favorite = !marsItem.favorite)
        return localDataSource.saveMarsList(listOf(updated))
    }
}
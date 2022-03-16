package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import kotlinx.coroutines.flow.Flow

class MarsRepository(
    private val localDataSource: com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource,
    private val remoteDataSource: com.mariomanzano.nasaexplorer.datasource.MarsRemoteDataSource
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
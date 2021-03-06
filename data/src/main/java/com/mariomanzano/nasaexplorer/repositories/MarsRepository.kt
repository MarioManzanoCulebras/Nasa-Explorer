package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarsRepository @Inject constructor(
    private val localDataSource: MarsLocalDataSource,
    private val remoteDataSource: MarsRemoteDataSource
) {

    val marsList = localDataSource.marsList

    fun findById(id: Int): Flow<MarsItem> = localDataSource.findMarsById(id)

    suspend fun requestMarsList(): Error? {
        val items = remoteDataSource.findMarsItems()
        items.fold(ifLeft = { return it }) { serverList ->
            if (serverList.isEmpty()) return Error.NoData
            localDataSource.saveMarsList(serverList)
        }
        return null
    }

    suspend fun switchFavorite(marsItem: MarsItem): Error? {
        val updated = marsItem.copy(favorite = !marsItem.favorite)
        return localDataSource.saveMarsFavoriteList(listOf(updated))
    }
}
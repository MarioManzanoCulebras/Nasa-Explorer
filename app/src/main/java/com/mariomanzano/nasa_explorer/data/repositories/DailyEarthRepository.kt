package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.datasource.EarthLocalDataSource
import com.mariomanzano.nasa_explorer.data.datasource.EarthRemoteDataSource
import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.Error
import kotlinx.coroutines.flow.Flow

class DailyEarthRepository(
    private val localDataSource: EarthLocalDataSource,
    private val remoteDataSource: EarthRemoteDataSource
) {

    val podList = localDataSource.earthList

    fun findById(id: Int): Flow<EarthItem> = localDataSource.findEarthById(id)

    suspend fun requestPODList(): Error? {
        if (localDataSource.isEarthListEmpty()) {
            val items = remoteDataSource.findEarthItems()
            items.fold(ifLeft = { return it }) {
                localDataSource.saveEarthList(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(earthItem: EarthItem): Error? {
        val updated = earthItem.copy(favorite = !earthItem.favorite)
        return localDataSource.saveEarthList(listOf(updated))
    }
}
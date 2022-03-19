package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.EarthRemoteDataSource
import kotlinx.coroutines.flow.Flow

class DailyEarthRepository(
    private val localDataSource: EarthLocalDataSource,
    private val remoteDataSource: EarthRemoteDataSource
) {

    val earthList = localDataSource.earthList

    fun findById(id: Int): Flow<EarthItem> = localDataSource.findEarthById(id)

    suspend fun requestEarthList(dayChanged: Boolean): Error? {
        if (dayChanged) localDataSource.saveEarthList(emptyList())
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
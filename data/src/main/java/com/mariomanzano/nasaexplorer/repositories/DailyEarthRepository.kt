package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.EarthRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyEarthRepository @Inject constructor(
    private val localDataSource: EarthLocalDataSource,
    private val remoteDataSource: EarthRemoteDataSource
) {

    val earthList = localDataSource.earthList

    fun findById(id: Int): Flow<EarthItem> = localDataSource.findEarthById(id)

    suspend fun requestEarthList(): Error? {
        val items = remoteDataSource.findEarthItems()
        items.fold(ifLeft = { return it }) { serverList ->
            if (serverList.isEmpty()) return Error.NoData
            localDataSource.saveEarthList(serverList)
        }
        return null
    }

    suspend fun switchFavorite(earthItem: EarthItem): Error? {
        val updated = earthItem.copy(favorite = !earthItem.favorite)
        return localDataSource.saveEarthFavoriteList(listOf(updated))
    }
}
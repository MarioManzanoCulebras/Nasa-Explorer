package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.EarthRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DailyEarthRepository(
    private val localDataSource: EarthLocalDataSource,
    private val remoteDataSource: EarthRemoteDataSource
) {

    val earthList = localDataSource.earthList

    fun findById(id: Int): Flow<EarthItem> = localDataSource.findEarthById(id)

    suspend fun requestEarthList(): Error? {
        val items = remoteDataSource.findEarthItems()
        items.fold(ifLeft = { return it }) { serverList ->
            earthList.first { true }.also { list ->
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
                localDataSource.saveEarthList(serverList)
            }
        }
        return null
    }

    suspend fun resetEarthList(): Error? {
        localDataSource.clearEarthList()
        return null
    }

    suspend fun switchFavorite(earthItem: EarthItem): Error? {
        val updated = earthItem.copy(favorite = !earthItem.favorite)
        return localDataSource.saveEarthList(listOf(updated))
    }
}
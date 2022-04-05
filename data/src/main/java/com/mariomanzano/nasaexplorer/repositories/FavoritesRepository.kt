package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val pODDataSource: PODLocalDataSource,
    private val earthDataSource: EarthLocalDataSource,
    private val marsDataSource: MarsLocalDataSource
) {

    private val podList = pODDataSource.podListFavorite
    private val earthList = earthDataSource.earthListFavorite
    private val marsList = marsDataSource.marsListFavorite

    fun getList() = merge(flowOf(podList, earthList, marsList))

    fun findByIdAndType(id: Int, type: String): Flow<NasaItem> {
        return when (type) {
            "dailyPicture" -> pODDataSource.findPODById(id)
            "earth" -> earthDataSource.findEarthById(id)
            else -> marsDataSource.findMarsById(id)
        }
    }


    suspend fun switchFavorite(id: Int, type: String, favorite: Boolean): Error? {
        return when (type) {
            "dailyPicture" -> {
                pODDataSource.updatePODList(id, favorite)
            }
            "earth" -> {
                earthDataSource.updateEarthList(id, favorite)
            }
            "mars" -> {
                marsDataSource.updateMarsList(id, favorite)
            }
            else -> null
        }

    }
}
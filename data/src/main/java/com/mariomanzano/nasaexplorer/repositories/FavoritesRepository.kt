package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

class FavoritesRepository(
    private val pODDataSource: PODLocalDataSource,
    private val earthDataSource: EarthLocalDataSource,
    private val marsDataSource: MarsLocalDataSource
) {

    private val podList = pODDataSource.podList
    private val earthList = earthDataSource.earthList
    private val marsList = marsDataSource.marsList

    fun getList() = merge(podList, earthList, marsList)

    fun findByIdAndType(id: Int, type: String): Flow<NasaItem> {
        return when (type) {
            "dailyPicture" -> pODDataSource.findPODById(id)
            "earth" -> earthDataSource.findEarthById(id)
            else -> marsDataSource.findMarsById(id)
        }
    }


    suspend fun switchFavorite(nasaItem: NasaItem): Error? {
        return when (nasaItem) {
            is PictureOfDayItem -> {
                val updatedPOD = nasaItem.copy(favorite = !nasaItem.favorite)
                pODDataSource.savePODList(listOf(updatedPOD))
            }
            is EarthItem -> {
                val updatedEarth = nasaItem.copy(favorite = !nasaItem.favorite)
                earthDataSource.saveEarthList(listOf(updatedEarth))
            }
            is MarsItem -> {
                val updatedMars = nasaItem.copy(favorite = !nasaItem.favorite)
                marsDataSource.saveMarsList(listOf(updatedMars))
            }
            else -> null
        }

    }
}
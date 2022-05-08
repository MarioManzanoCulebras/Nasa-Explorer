package com.mariomanzano.nasaexplorer.datasource

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import kotlinx.coroutines.flow.Flow

interface EarthLocalDataSource {
    val earthList: Flow<List<EarthItem>>
    val earthListFavorite: Flow<List<EarthItem>>

    fun findEarthById(id: Int): Flow<EarthItem>
    suspend fun saveEarthFavoriteList(items: List<EarthItem>): Error?
    suspend fun saveEarthList(items: List<EarthItem>): Error?
    suspend fun updateEarthList(id: Int, favorite: Boolean): Error?
}
package com.mariomanzano.nasaexplorer.datasource

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import kotlinx.coroutines.flow.Flow

interface EarthLocalDataSource {
    val earthList: Flow<List<EarthItem>>

    suspend fun isEarthListEmpty(): Boolean
    fun findEarthById(id: Int): Flow<EarthItem>
    suspend fun saveEarthList(items: List<EarthItem>): Error?
    suspend fun insertEarthFavorite(item: EarthItem): Error?
}
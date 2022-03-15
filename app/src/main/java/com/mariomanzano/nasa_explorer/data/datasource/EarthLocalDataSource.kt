package com.mariomanzano.nasa_explorer.data.datasource

import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.Error
import kotlinx.coroutines.flow.Flow

interface EarthLocalDataSource {
    val earthList: Flow<List<EarthItem>>

    suspend fun isEarthListEmpty(): Boolean
    fun findEarthById(id: Int): Flow<EarthItem>
    suspend fun saveEarthList(items: List<EarthItem>): Error?
}
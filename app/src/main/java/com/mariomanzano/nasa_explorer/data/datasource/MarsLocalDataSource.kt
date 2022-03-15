package com.mariomanzano.nasa_explorer.data.datasource

import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import kotlinx.coroutines.flow.Flow

interface MarsLocalDataSource {
    val marsList: Flow<List<MarsItem>>

    suspend fun isMarsListEmpty(): Boolean
    fun findMarsById(id: Int): Flow<MarsItem>
    suspend fun saveMarsList(items: List<MarsItem>): Error?
}
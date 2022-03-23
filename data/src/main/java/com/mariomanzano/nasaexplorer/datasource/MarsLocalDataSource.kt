package com.mariomanzano.nasaexplorer.datasource

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import kotlinx.coroutines.flow.Flow

interface MarsLocalDataSource {
    val marsList: Flow<List<MarsItem>>

    suspend fun isMarsListEmpty(): Boolean
    fun findMarsById(id: Int): Flow<MarsItem>
    suspend fun saveMarsList(items: List<MarsItem>): Error?
    suspend fun updateMarsList(id: Int, favorite: Boolean): Error?
    suspend fun clearMarsList()
}
package com.mariomanzano.nasaexplorer.datasource

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import kotlinx.coroutines.flow.Flow

interface MarsLocalDataSource {
    val marsList: Flow<List<MarsItem>>
    val marsListFavorite: Flow<List<MarsItem>>

    fun findMarsById(id: Int): Flow<MarsItem>
    suspend fun saveMarsFavoriteList(items: List<MarsItem>): Error?
    suspend fun saveMarsList(items: List<MarsItem>): Error?
    suspend fun updateMarsList(id: Int, favorite: Boolean): Error?
}
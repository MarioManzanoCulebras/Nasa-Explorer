package com.mariomanzano.nasaexplorer.datasource

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import kotlinx.coroutines.flow.Flow

interface PODLocalDataSource {
    val podList: Flow<List<PictureOfDayItem>>
    val podListFavorite: Flow<List<PictureOfDayItem>>

    fun findPODById(id: Int): Flow<PictureOfDayItem>
    suspend fun savePODFavoriteList(items: List<PictureOfDayItem>): Error?
    suspend fun savePODList(items: List<PictureOfDayItem>): Error?
    suspend fun updatePODList(id: Int, favorite: Boolean): Error?
}
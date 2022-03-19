package com.mariomanzano.nasaexplorer.datasource

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import kotlinx.coroutines.flow.Flow
import java.util.*

interface EarthLocalDataSource {
    val earthList: Flow<List<EarthItem>>

    suspend fun isEarthListEmpty(): Boolean
    fun findEarthById(id: Int): Flow<EarthItem>
    fun findEarthByIdAndDate(id: Int, date: Calendar): Flow<EarthItem>
    suspend fun saveEarthList(items: List<EarthItem>): Error?
}
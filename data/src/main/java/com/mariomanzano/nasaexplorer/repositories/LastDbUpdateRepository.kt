package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.nasaexplorer.datasource.LastDbUpdateDataSource
import java.util.*

class LastDbUpdateRepository(
    private val lastDbUpdateDataSource: LastDbUpdateDataSource,
) {
    val lastPODUpdate = lastDbUpdateDataSource.podTableUpdatedDay
    val lastEarthUpdate = lastDbUpdateDataSource.earthTableUpdatedDay
    val lastMarsUpdate = lastDbUpdateDataSource.marsTableUpdatedDay

    suspend fun updatePODLastUpdate(date: Calendar) = lastDbUpdateDataSource.updatePODDate(date)
    suspend fun updateEarthLastUpdate(date: Calendar) = lastDbUpdateDataSource.updateEarthDate(date)
    suspend fun updateMarsLastUpdate(date: Calendar) = lastDbUpdateDataSource.updateMarsDate(date)
}
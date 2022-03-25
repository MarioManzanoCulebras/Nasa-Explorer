package com.mariomanzano.nasaexplorer.repositories

import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.nasaexplorer.datasource.LastDbUpdateDataSource

class LastDbUpdateRepository(
    private val lastDbUpdateDataSource: LastDbUpdateDataSource,
) {
    val lastPODUpdate = lastDbUpdateDataSource.podTableUpdatedDay
    val lastEarthUpdate = lastDbUpdateDataSource.earthTableUpdatedDay
    val lastMarsUpdate = lastDbUpdateDataSource.marsTableUpdatedDay

    suspend fun updatePODLastUpdate(item: LastUpdateInfo?) =
        lastDbUpdateDataSource.updatePODDate(item)

    suspend fun updateEarthLastUpdate(item: LastUpdateInfo?) =
        lastDbUpdateDataSource.updateEarthDate(item)

    suspend fun updateMarsLastUpdate(item: LastUpdateInfo?) =
        lastDbUpdateDataSource.updateMarsDate(item)
}
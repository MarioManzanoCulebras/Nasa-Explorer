package com.mariomanzano.nasaexplorer.datasource

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.LastUpdateInfo
import kotlinx.coroutines.flow.Flow

interface LastDbUpdateDataSource {

    val podTableUpdatedDay: Flow<LastUpdateInfo?>
    val earthTableUpdatedDay: Flow<LastUpdateInfo?>
    val marsTableUpdatedDay: Flow<LastUpdateInfo?>

    suspend fun updatePODDate(item: LastUpdateInfo?): Error?
    suspend fun updateEarthDate(item: LastUpdateInfo?): Error?
    suspend fun updateMarsDate(item: LastUpdateInfo?): Error?
}
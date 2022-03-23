package com.mariomanzano.nasaexplorer.datasource

import com.mariomanzano.domain.Error
import kotlinx.coroutines.flow.Flow
import java.util.*

interface LastDbUpdateDataSource {
    val podTableUpdatedDay: Flow<Calendar?>
    val earthTableUpdatedDay: Flow<Calendar?>
    val marsTableUpdatedDay: Flow<Calendar?>

    suspend fun updatePODDate(date: Calendar): Error?
    suspend fun updateEarthDate(date: Calendar): Error?
    suspend fun updateMarsDate(date: Calendar): Error?
}
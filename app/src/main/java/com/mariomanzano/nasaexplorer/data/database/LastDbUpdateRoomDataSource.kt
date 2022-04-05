package com.mariomanzano.nasaexplorer.data.database

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.nasaexplorer.datasource.LastDbUpdateDataSource
import com.mariomanzano.nasaexplorer.network.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class LastDbUpdateRoomDataSource @Inject constructor(private val nasaDao: NasaDao) :
    LastDbUpdateDataSource {

    override val podTableUpdatedDay: Flow<LastUpdateInfo?> =
        nasaDao.getPODLastUpdate().map { it?.toLastDomainModel() }

    override val earthTableUpdatedDay: Flow<LastUpdateInfo?> =
        nasaDao.getEarthLastUpdate().map { it?.toLastDomainModel() }

    override val marsTableUpdatedDay: Flow<LastUpdateInfo?> =
        nasaDao.getMarsLastUpdate().map { it?.toLastDomainModel() }

    override suspend fun updatePODDate(item: LastUpdateInfo?): Error? =
        tryCall {
            val save = item ?: LastUpdateInfo(0, Calendar.getInstance(), true)
            nasaDao.updatePODDbLastUpdate(save.fromDomainPODModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun updateEarthDate(item: LastUpdateInfo?): Error? =
        tryCall {
            val save = item ?: LastUpdateInfo(0, Calendar.getInstance(), true)
            nasaDao.updateEarthDbLastUpdate(save.fromDomainEarthModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun updateMarsDate(item: LastUpdateInfo?): Error? =
        tryCall {
            val save = item ?: LastUpdateInfo(0, Calendar.getInstance(), true)
            nasaDao.updateMarsDbLastUpdate(save.fromDomainMarsModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )
}

private fun DbPODLastUpdate.toLastDomainModel(): LastUpdateInfo =
    LastUpdateInfo(id, date, updatedNeed)

private fun DbEarthLastUpdate.toLastDomainModel(): LastUpdateInfo =
    LastUpdateInfo(id, date, updatedNeed)

private fun DbMarsLastUpdate.toLastDomainModel(): LastUpdateInfo =
    LastUpdateInfo(id, date, updatedNeed)

private fun LastUpdateInfo.fromDomainPODModel(): DbPODLastUpdate = DbPODLastUpdate(
    id = id,
    date = date,
    updatedNeed = updateNeed
)

private fun LastUpdateInfo.fromDomainEarthModel(): DbEarthLastUpdate = DbEarthLastUpdate(
    id = id,
    date = date,
    updatedNeed = updateNeed
)

private fun LastUpdateInfo.fromDomainMarsModel(): DbMarsLastUpdate = DbMarsLastUpdate(
    id = id,
    date = date,
    updatedNeed = updateNeed
)

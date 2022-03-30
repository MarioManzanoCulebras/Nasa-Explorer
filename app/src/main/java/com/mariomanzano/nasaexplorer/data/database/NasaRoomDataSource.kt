package com.mariomanzano.nasaexplorer.data.database

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.LastDbUpdateDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import com.mariomanzano.nasaexplorer.network.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class NasaRoomDataSource(private val nasaDao: NasaDao) : PODLocalDataSource, EarthLocalDataSource,
    MarsLocalDataSource, LastDbUpdateDataSource {

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

    override val podList: Flow<List<PictureOfDayItem>> =
        nasaDao.getAllPOD().map { it.toPODDomainModel() }

    override val earthList: Flow<List<EarthItem>> =
        nasaDao.getAllEarth().map { it.toEarthDomainModel() }

    override val marsList: Flow<List<MarsItem>> =
        nasaDao.getAllMars().map { it.toMarsDomainModel() }

    override suspend fun isPODListEmpty(): Boolean = nasaDao.getPODCount() == 0

    override suspend fun isEarthListEmpty(): Boolean = nasaDao.getEarthCount() == 0

    override suspend fun isMarsListEmpty(): Boolean = nasaDao.getMarsCount() == 0

    override fun findPODById(id: Int): Flow<PictureOfDayItem> =
        nasaDao.findPODById(id).map { it.toDomainModel() }

    override fun findEarthById(id: Int): Flow<EarthItem> =
        nasaDao.findEarthById(id).map { it.toDomainModel() }

    override fun findMarsById(id: Int): Flow<MarsItem> =
        nasaDao.findMarsById(id).map { it.toDomainModel() }

    override suspend fun savePODList(items: List<PictureOfDayItem>): Error? =
        tryCall {
            nasaDao.insertPODOnDb(items.fromPODDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun updatePODList(id: Int, favorite: Boolean): Error? =
        tryCall {
            nasaDao.updatePODEntities(id, favorite)
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun saveEarthFavoriteList(items: List<EarthItem>): Error? =
        tryCall {
            nasaDao.insertEarthOnDb(items.fromEarthDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun saveEarthList(items: List<EarthItem>): Error? =
        tryCall {
            nasaDao.insertEarthEntities(items.fromEarthDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun updateEarthList(id: Int, favorite: Boolean): Error? =
        tryCall {
            nasaDao.updateEarthEntities(id, favorite)
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun saveMarsFavoriteList(items: List<MarsItem>): Error? =
        tryCall {
            nasaDao.insertMarsOnDb(items.fromMarsDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun saveMarsList(items: List<MarsItem>): Error? =
        tryCall {
            nasaDao.insertMarsEntities(items.fromMarsDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun updateMarsList(id: Int, favorite: Boolean): Error? =
        tryCall {
            nasaDao.updateMarsEntities(id, favorite)
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

private fun List<DbPOD>.toPODDomainModel(): List<PictureOfDayItem> =
    map { it.toDomainModel() }

private fun List<DbEarth>.toEarthDomainModel(): List<EarthItem> =
    map { it.toDomainModel() }

private fun List<DbMars>.toMarsDomainModel(): List<MarsItem> =
    map { it.toDomainModel() }

private fun DbPOD.toDomainModel(): PictureOfDayItem =
    PictureOfDayItem(
        id = id,
        date = date,
        title = title,
        description = description,
        url = url,
        mediaType = mediaType,
        favorite = favorite,
        copyRight = copyRight
    )

private fun DbEarth.toDomainModel(): EarthItem =
    EarthItem(
        id,
        date = date,
        title = title,
        description = description,
        url = url,
        mediaType = "image",
        favorite = favorite
    )

private fun DbMars.toDomainModel(): MarsItem =
    MarsItem(
        id = id,
        date = date,
        title = title,
        description = description,
        url = url,
        mediaType = "image",
        favorite = favorite,
        sun = sun,
        cameraName = cameraName ?: "",
        roverName = roverName ?: "",
        roverLandingDate = roverLandingDate,
        roverLaunchingDate = roverLaunchingDate,
        roverMissionStatus = roverMissionStatus ?: "",
    )

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

private fun List<PictureOfDayItem>.fromPODDomainModel(): List<DbPOD> =
    map { it.fromDomainModel() }

private fun PictureOfDayItem.fromDomainModel(): DbPOD = DbPOD(
    id = id,
    date = date,
    title = title,
    description = description,
    url = url,
    mediaType = mediaType,
    copyRight = copyRight,
    favorite = favorite,
    type = type
)

private fun List<EarthItem>.fromEarthDomainModel(): List<DbEarth> =
    map { it.fromDomainModel() }

private fun EarthItem.fromDomainModel(): DbEarth = DbEarth(
    id = id,
    date = date,
    title = title,
    description = description,
    url = url,
    favorite = favorite,
    type = type
)

private fun List<MarsItem>.fromMarsDomainModel(): List<DbMars> =
    map { it.fromDomainModel() }

private fun MarsItem.fromDomainModel(): DbMars = DbMars(
    id = id,
    date = date,
    title = title,
    description = description,
    url = url,
    sun = sun,
    cameraName = cameraName,
    roverName = roverName,
    roverLandingDate = roverLandingDate,
    roverLaunchingDate = roverLaunchingDate,
    roverMissionStatus = roverMissionStatus,
    favorite = favorite,
    type = type
)

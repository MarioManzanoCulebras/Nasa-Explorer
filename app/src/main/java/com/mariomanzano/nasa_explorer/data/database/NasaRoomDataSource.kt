package com.mariomanzano.nasa_explorer.data.database

import com.mariomanzano.nasa_explorer.data.datasource.EarthLocalDataSource
import com.mariomanzano.nasa_explorer.data.datasource.MarsLocalDataSource
import com.mariomanzano.nasa_explorer.data.datasource.PODLocalDataSource
import com.mariomanzano.nasa_explorer.data.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class NasaRoomDataSource(private val nasaDao: NasaDao) : PODLocalDataSource, EarthLocalDataSource,
    MarsLocalDataSource {

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

    override suspend fun savePODList(items: List<PictureOfDayItem>): Error? = tryCall {
        nasaDao.insertPODEntities(items.fromPODDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )

    override suspend fun saveEarthList(items: List<EarthItem>): Error? = tryCall {
        nasaDao.insertEarthEntities(items.fromEarthDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )

    override suspend fun saveMarsList(items: List<MarsItem>): Error? = tryCall {
        nasaDao.insertMarsEntities(items.fromMarsDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbPOD>.toPODDomainModel(): List<PictureOfDayItem> = map { it.toDomainModel() }

private fun List<DbEarth>.toEarthDomainModel(): List<EarthItem> = map { it.toDomainModel() }

private fun List<DbMars>.toMarsDomainModel(): List<MarsItem> = map { it.toDomainModel() }

private fun DbPOD.toDomainModel(): PictureOfDayItem =
    PictureOfDayItem(
        id,
        Calendar.getInstance().apply { time.time = date },
        title,
        description,
        url,
        favorite,
        copyRight
    )

private fun DbEarth.toDomainModel(): EarthItem =
    EarthItem(
        id,
        Calendar.getInstance().apply { time.time = date },
        title,
        description,
        url,
        favorite
    )

private fun DbMars.toDomainModel(): MarsItem =
    MarsItem(
        id,
        Calendar.getInstance().apply { time.time = date },
        title,
        description,
        url,
        favorite,
        sun,
        cameraName ?: "",
        roverName ?: "",
        Calendar.getInstance().apply { time.time = roverLandingDate ?: 0L },
        Calendar.getInstance().apply { time.time = roverLaunchingDate ?: 0L },
        roverMissionStatus ?: ""
    )

private fun List<PictureOfDayItem>.fromPODDomainModel(): List<DbPOD> = map { it.fromDomainModel() }

private fun PictureOfDayItem.fromDomainModel(): DbPOD = DbPOD(
    id,
    date.time.time,
    title,
    description,
    url,
    copyRight,
    favorite
)

private fun List<EarthItem>.fromEarthDomainModel(): List<DbEarth> = map { it.fromDomainModel() }

private fun EarthItem.fromDomainModel(): DbEarth = DbEarth(
    id,
    date.time.time,
    title,
    description,
    url,
    favorite
)

private fun List<MarsItem>.fromMarsDomainModel(): List<DbMars> = map { it.fromDomainModel() }

private fun MarsItem.fromDomainModel(): DbMars = DbMars(
    id,
    date.time.time,
    title,
    description,
    url,
    sun,
    cameraName,
    roverName,
    roverLandingDate.time.time,
    roverLaunchingDate.time.time,
    roverMissionStatus,
    favorite
)
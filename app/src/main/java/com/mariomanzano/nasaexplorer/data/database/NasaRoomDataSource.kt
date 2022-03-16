package com.mariomanzano.nasaexplorer.data.database

import com.mariomanzano.domain.Error
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import com.mariomanzano.nasaexplorer.network.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NasaRoomDataSource(private val nasaDao: NasaDao) : PODLocalDataSource, EarthLocalDataSource,
    MarsLocalDataSource {

    override val podList: Flow<List<com.mariomanzano.domain.entities.PictureOfDayItem>> =
        nasaDao.getAllPOD().map { it.toPODDomainModel() }

    override val earthList: Flow<List<com.mariomanzano.domain.entities.EarthItem>> =
        nasaDao.getAllEarth().map { it.toEarthDomainModel() }

    override val marsList: Flow<List<com.mariomanzano.domain.entities.MarsItem>> =
        nasaDao.getAllMars().map { it.toMarsDomainModel() }

    override suspend fun isPODListEmpty(): Boolean = nasaDao.getPODCount() == 0

    override suspend fun isEarthListEmpty(): Boolean = nasaDao.getEarthCount() == 0

    override suspend fun isMarsListEmpty(): Boolean = nasaDao.getMarsCount() == 0

    override fun findPODById(id: Int): Flow<com.mariomanzano.domain.entities.PictureOfDayItem> =
        nasaDao.findPODById(id).map { it.toDomainModel() }

    override fun findEarthById(id: Int): Flow<com.mariomanzano.domain.entities.EarthItem> =
        nasaDao.findEarthById(id).map { it.toDomainModel() }

    override fun findMarsById(id: Int): Flow<com.mariomanzano.domain.entities.MarsItem> =
        nasaDao.findMarsById(id).map { it.toDomainModel() }

    override suspend fun savePODList(items: List<com.mariomanzano.domain.entities.PictureOfDayItem>): Error? =
        tryCall {
            nasaDao.insertPODEntities(items.fromPODDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun saveEarthList(items: List<com.mariomanzano.domain.entities.EarthItem>): Error? =
        tryCall {
            nasaDao.insertEarthEntities(items.fromEarthDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun saveMarsList(items: List<com.mariomanzano.domain.entities.MarsItem>): Error? =
        tryCall {
            nasaDao.insertMarsEntities(items.fromMarsDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )
}

private fun List<DbPOD>.toPODDomainModel(): List<com.mariomanzano.domain.entities.PictureOfDayItem> =
    map { it.toDomainModel() }

private fun List<DbEarth>.toEarthDomainModel(): List<com.mariomanzano.domain.entities.EarthItem> =
    map { it.toDomainModel() }

private fun List<DbMars>.toMarsDomainModel(): List<com.mariomanzano.domain.entities.MarsItem> =
    map { it.toDomainModel() }

private fun DbPOD.toDomainModel(): com.mariomanzano.domain.entities.PictureOfDayItem =
    com.mariomanzano.domain.entities.PictureOfDayItem(
        id,
        date,
        title,
        description,
        url,
        favorite,
        copyRight
    )

private fun DbEarth.toDomainModel(): com.mariomanzano.domain.entities.EarthItem =
    com.mariomanzano.domain.entities.EarthItem(
        id,
        date,
        title,
        description,
        url,
        favorite
    )

private fun DbMars.toDomainModel(): com.mariomanzano.domain.entities.MarsItem =
    com.mariomanzano.domain.entities.MarsItem(
        id,
        date,
        title,
        description,
        url,
        favorite,
        sun,
        cameraName ?: "",
        roverName ?: "",
        roverLandingDate,
        roverLaunchingDate,
        roverMissionStatus ?: ""
    )

private fun List<com.mariomanzano.domain.entities.PictureOfDayItem>.fromPODDomainModel(): List<DbPOD> =
    map { it.fromDomainModel() }

private fun com.mariomanzano.domain.entities.PictureOfDayItem.fromDomainModel(): DbPOD = DbPOD(
    id,
    date,
    title,
    description,
    url,
    copyRight,
    favorite
)

private fun List<com.mariomanzano.domain.entities.EarthItem>.fromEarthDomainModel(): List<DbEarth> =
    map { it.fromDomainModel() }

private fun com.mariomanzano.domain.entities.EarthItem.fromDomainModel(): DbEarth = DbEarth(
    id,
    date,
    title,
    description,
    url,
    favorite
)

private fun List<com.mariomanzano.domain.entities.MarsItem>.fromMarsDomainModel(): List<DbMars> =
    map { it.fromDomainModel() }

private fun com.mariomanzano.domain.entities.MarsItem.fromDomainModel(): DbMars = DbMars(
    id,
    date,
    title,
    description,
    url,
    sun,
    cameraName,
    roverName,
    roverLandingDate,
    roverLaunchingDate,
    roverMissionStatus,
    favorite
)
package com.mariomanzano.nasaexplorer.data.database

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.network.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarsRoomDataSource @Inject constructor(private val nasaDao: NasaDao) : MarsLocalDataSource {

    override val marsList: Flow<List<MarsItem>> =
        nasaDao.getAllMars().map { it.toMarsDomainModel() }

    override val marsListFavorite: Flow<List<MarsItem>> =
        nasaDao.getAllMars().map { mars -> mars.toMarsDomainModel().filter { it.favorite } }

    override suspend fun isMarsListEmpty(): Boolean = nasaDao.getMarsCount() == 0

    override fun findMarsById(id: Int): Flow<MarsItem> =
        nasaDao.findMarsById(id).map { it.toDomainModel() }

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

private fun List<DbMars>.toMarsDomainModel(): List<MarsItem> =
    map { it.toDomainModel() }

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

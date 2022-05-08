package com.mariomanzano.nasaexplorer.data.database

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import com.mariomanzano.nasaexplorer.network.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PODRoomDataSource @Inject constructor(private val nasaDao: NasaDao) : PODLocalDataSource {

    override val podList: Flow<List<PictureOfDayItem>> =
        nasaDao.getAllPOD().map { it.toPODDomainModel() }

    override val podListFavorite: Flow<List<PictureOfDayItem>> =
        nasaDao.getAllPOD().map { pod -> pod.toPODDomainModel().filter { it.favorite } }

    override fun findPODById(id: Int): Flow<PictureOfDayItem> =
        nasaDao.findPODById(id).map { it.toDomainModel() }

    override suspend fun savePODFavoriteList(items: List<PictureOfDayItem>): Error? =
        tryCall {
            nasaDao.insertPODOnDb(items.fromPODDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )


    override suspend fun savePODList(items: List<PictureOfDayItem>): Error? =
        tryCall {
            nasaDao.insertPODEntities(items.fromPODDomainModel())
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
}

private fun List<DbPOD>.toPODDomainModel(): List<PictureOfDayItem> =
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

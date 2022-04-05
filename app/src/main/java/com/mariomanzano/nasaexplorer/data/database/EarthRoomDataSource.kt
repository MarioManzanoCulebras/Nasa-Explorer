package com.mariomanzano.nasaexplorer.data.database

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.network.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EarthRoomDataSource @Inject constructor(private val nasaDao: NasaDao) : EarthLocalDataSource {

    override val earthList: Flow<List<EarthItem>> =
        nasaDao.getAllEarth().map { it.toEarthDomainModel() }

    override val earthListFavorite: Flow<List<EarthItem>> =
        nasaDao.getAllEarth().map { earth -> earth.toEarthDomainModel().filter { it.favorite } }

    override suspend fun isEarthListEmpty(): Boolean = nasaDao.getEarthCount() == 0

    override fun findEarthById(id: Int): Flow<EarthItem> =
        nasaDao.findEarthById(id).map { it.toDomainModel() }

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
}

private fun List<DbEarth>.toEarthDomainModel(): List<EarthItem> =
    map { it.toDomainModel() }

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

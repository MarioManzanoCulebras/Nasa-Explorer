package com.mariomanzano.nasaexplorer.ui

import com.mariomanzano.nasaexplorer.FakeNasaDao
import com.mariomanzano.nasaexplorer.FakeRemoteEarthService
import com.mariomanzano.nasaexplorer.FakeRemotePODService
import com.mariomanzano.nasaexplorer.data.database.*
import com.mariomanzano.nasaexplorer.network.EarthServerDataSource
import com.mariomanzano.nasaexplorer.network.PODServerDataSource
import com.mariomanzano.nasaexplorer.network.entities.ApiAPOD
import com.mariomanzano.nasaexplorer.network.entities.ApiEPIC
import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import java.util.*

//DailyPictures helper

fun buildDailyPictureRepositoryWith(
    localListPOD: List<DbPOD> = emptyList(),
    remoteData: List<ApiAPOD>
): DailyPicturesRepository {
    val podLocalDataSource = PODRoomDataSource(FakeNasaDao(listPOD = localListPOD))
    val podRemoteDataSource = PODServerDataSource(FakeRemotePODService(remoteData))
    return DailyPicturesRepository(podLocalDataSource, podRemoteDataSource)
}

fun buildLastDbUpdateRepositoryWithPodList(
    localListPOD: List<DbPOD> = emptyList()
): LastDbUpdateRepository {
    return LastDbUpdateRepository(LastDbUpdateRoomDataSource(FakeNasaDao(listPOD = localListPOD)))
}

fun buildDatabasePods(vararg id: Int) = id.map {
    DbPOD(
        id = it,
        title = "Title $it",
        description = "Description $it",
        date = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, it) },
        url = "url $it",
        mediaType = "image",
        copyRight = "copyRight $it",
        favorite = false,
        type = "type $it"
    )
}

fun buildRemotePods(vararg id: Int) = id.map {
    ApiAPOD(
        copyRight = "copyRight $it",
        date = "2022-05-16",
        title = "Title $it",
        explanation = "explanation $it",
        media_type = "image $it",
        url = "url $it"
    )
}

//Earth helper
fun buildDailyEarthRepositoryWith(
    localListEarth: List<DbEarth> = emptyList(),
    remoteData: List<ApiEPIC>
): DailyEarthRepository {
    val earthLocalDataSource =
        EarthRoomDataSource(FakeNasaDao(listEarth = localListEarth.toMutableList()))
    val earthRemoteDataSource = EarthServerDataSource(FakeRemoteEarthService(remoteData))
    return DailyEarthRepository(earthLocalDataSource, earthRemoteDataSource)
}

fun buildLastDbUpdateRepositoryWithEarthList(
    localListEarth: List<DbEarth> = emptyList()
): LastDbUpdateRepository {
    return LastDbUpdateRepository(LastDbUpdateRoomDataSource(FakeNasaDao(listEarth = localListEarth.toMutableList())))
}

fun buildDatabaseEarth(vararg id: Int) = id.map {
    DbEarth(
        id = it,
        title = "Title $it",
        description = "Description $it",
        date = Calendar.getInstance().apply { set(Calendar.MONTH, it) },
        url = "url $it",
        favorite = false,
        type = "type $it"
    )
}

fun buildRemoteEarth(vararg id: Int) = id.map {
    ApiEPIC(
        identifier = "Identifier  $it",
        date = "2015-10-31 00:31:45",
        caption = "Title  $it",
        image = "Image  $it"
    )
}
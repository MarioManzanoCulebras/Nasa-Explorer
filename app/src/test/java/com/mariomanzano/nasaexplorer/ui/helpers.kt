package com.mariomanzano.nasaexplorer.ui

import com.mariomanzano.nasaexplorer.FakeNasaDao
import com.mariomanzano.nasaexplorer.FakeRemoteEarthService
import com.mariomanzano.nasaexplorer.FakeRemoteMarsService
import com.mariomanzano.nasaexplorer.FakeRemotePODService
import com.mariomanzano.nasaexplorer.data.database.*
import com.mariomanzano.nasaexplorer.network.EarthServerDataSource
import com.mariomanzano.nasaexplorer.network.MarsServerDataSource
import com.mariomanzano.nasaexplorer.network.PODServerDataSource
import com.mariomanzano.nasaexplorer.network.entities.*
import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
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

//Mars helper
fun buildMarsRepositoryWith(
    localListMars: List<DbMars> = emptyList(),
    remoteData: List<ApiMars>
): MarsRepository {
    val marsLocalDataSource =
        MarsRoomDataSource(FakeNasaDao(listMars = localListMars.toMutableList()))
    val marsRemoteDataSource = MarsServerDataSource(FakeRemoteMarsService(remoteData))
    return MarsRepository(marsLocalDataSource, marsRemoteDataSource)
}

fun buildLastDbUpdateRepositoryWithMarsList(
    localListMars: List<DbMars> = emptyList()
): LastDbUpdateRepository {
    return LastDbUpdateRepository(LastDbUpdateRoomDataSource(FakeNasaDao(listMars = localListMars.toMutableList())))
}

fun buildDatabaseMars(vararg id: Int) = id.map {
    DbMars(
        id = it,
        sun = it,
        title = "Title $it",
        description = "Description $it",
        date = Calendar.getInstance().apply { set(Calendar.MONTH, it) },
        url = "url $it",
        favorite = false,
        type = "type $it",
        cameraName = "cameraName $it",
        roverName = "roverName",
        roverLandingDate = Calendar.getInstance().apply { set(Calendar.MONTH, it) },
        roverLaunchingDate = Calendar.getInstance().apply { set(Calendar.MONTH, it) },
        roverMissionStatus = "active"
    )
}

fun buildRemoteMars(vararg id: Int) = id.map {
    ApiMars(
        photos = id.map { buildRemoteMarsItem(it) })
}

private fun buildRemoteMarsItem(id: Int) =
    ApiMarsItem(
        id = id,
        sol = 5000 + id,
        img_src = "img_src$id",
        earth_date = "2022-05-16",
        camera = ApiMarsCamera(id = 2, name = "name", full_name = "full_name"),
        rover = ApiMarsRover(
            name = "name",
            landing_date = "2022-05-16",
            launching_date = "2022-05-16",
            status = "active"
        ),
    )
package com.mariomanzano.nasaexplorer.ui

import com.mariomanzano.nasaexplorer.FakeNasaDao
import com.mariomanzano.nasaexplorer.FakeRemotePODService
import com.mariomanzano.nasaexplorer.data.database.DbPOD
import com.mariomanzano.nasaexplorer.data.database.LastDbUpdateRoomDataSource
import com.mariomanzano.nasaexplorer.data.database.PODRoomDataSource
import com.mariomanzano.nasaexplorer.network.PODServerDataSource
import com.mariomanzano.nasaexplorer.network.entities.ApiAPOD
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import java.util.*

fun buildDailyPictureRepositoryWith(
    localListPOD: List<DbPOD> = emptyList(),
    remoteData: List<ApiAPOD>
): DailyPicturesRepository {
    val podLocalDataSource = PODRoomDataSource(FakeNasaDao(listPOD = localListPOD))
    val podRemoteDataSource = PODServerDataSource(FakeRemotePODService(remoteData))
    return DailyPicturesRepository(podLocalDataSource, podRemoteDataSource)
}

fun buildLastDbUpdateRepositoryWith(
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
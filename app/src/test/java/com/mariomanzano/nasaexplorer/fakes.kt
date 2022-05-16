package com.mariomanzano.nasaexplorer

import com.mariomanzano.nasaexplorer.data.database.*
import com.mariomanzano.nasaexplorer.network.DailyEarthService
import com.mariomanzano.nasaexplorer.network.DailyPicturesService
import com.mariomanzano.nasaexplorer.network.MarsService
import com.mariomanzano.nasaexplorer.network.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeNasaDao(
    listPOD: List<DbPOD> = emptyList(),
    podLastUpdate: DbPODLastUpdate? = null,
    listEarth: List<DbEarth> = emptyList(),
    earthLasUpdate: DbEarthLastUpdate? = null,
    listMars: List<DbMars> = emptyList(),
    marsLasUpdate: DbMarsLastUpdate? = null
) : NasaDao {

    private val inMemoryPod = MutableStateFlow(listPOD)
    private val inMemoryPodLastUpdate = MutableStateFlow(podLastUpdate)

    private val inMemoryEarth = MutableStateFlow(listEarth)
    private val inMemoryEarthLastUpdate = MutableStateFlow(earthLasUpdate)

    private val inMemoryMars = MutableStateFlow(listMars)
    private val inMemoryMarsLastUpdate = MutableStateFlow(marsLasUpdate)

    private lateinit var findPODFlow: MutableStateFlow<DbPOD>
    private lateinit var findLastPODFlow: MutableStateFlow<DbPODLastUpdate?>

    private lateinit var findEarthFlow: MutableStateFlow<DbEarth>
    private lateinit var findLastEarthFlow: MutableStateFlow<DbEarthLastUpdate?>

    private lateinit var findMarsFlow: MutableStateFlow<DbMars>
    private lateinit var findLastMarsFlow: MutableStateFlow<DbMarsLastUpdate?>


    override fun getPODLastUpdate(): Flow<DbPODLastUpdate?> = inMemoryPodLastUpdate

    override fun getEarthLastUpdate(): Flow<DbEarthLastUpdate?> = inMemoryEarthLastUpdate

    override fun getMarsLastUpdate(): Flow<DbMarsLastUpdate?> = inMemoryMarsLastUpdate

    override suspend fun updatePODDbLastUpdate(item: DbPODLastUpdate) {
        inMemoryPodLastUpdate.value = item

        if (::findLastPODFlow.isInitialized) {
            inMemoryPodLastUpdate.value = item
        }
    }

    override suspend fun updateEarthDbLastUpdate(item: DbEarthLastUpdate) {
        inMemoryEarthLastUpdate.value = item

        if (::findLastEarthFlow.isInitialized) {
            inMemoryEarthLastUpdate.value = item
        }
    }

    override suspend fun updateMarsDbLastUpdate(item: DbMarsLastUpdate) {
        inMemoryMarsLastUpdate.value = item

        if (::findLastMarsFlow.isInitialized) {
            inMemoryMarsLastUpdate.value = item
        }
    }

    override fun getAllPOD(): Flow<List<DbPOD>> = inMemoryPod

    override fun getAllEarth(): Flow<List<DbEarth>> = inMemoryEarth

    override fun getAllMars(): Flow<List<DbMars>> = inMemoryMars

    override fun findPODById(id: Int): Flow<DbPOD> {
        findPODFlow = MutableStateFlow(inMemoryPod.value.first { it.id == id })
        return findPODFlow
    }

    override fun findEarthById(id: Int): Flow<DbEarth> {
        findEarthFlow = MutableStateFlow(inMemoryEarth.value.first { it.id == id })
        return findEarthFlow
    }

    override fun findMarsById(id: Int): Flow<DbMars> {
        findMarsFlow = MutableStateFlow(inMemoryMars.value.first { it.id == id })
        return findMarsFlow
    }

    override suspend fun getPODCount(): Int = inMemoryPod.value.size

    override suspend fun getEarthCount(): Int = inMemoryEarth.value.size

    override suspend fun getMarsCount(): Int = inMemoryMars.value.size

    override suspend fun insertPODOnDb(items: List<DbPOD>) {
        inMemoryPod.value = items

        if (::findPODFlow.isInitialized) {
            items.firstOrNull() { it.id == findPODFlow.value.id }
                ?.let { findPODFlow.value = it }
        }
    }

    override suspend fun insertPODEntities(items: List<DbPOD>) {
        inMemoryPod.value = items

        if (::findPODFlow.isInitialized) {
            items.firstOrNull() { it.id == findPODFlow.value.id }
                ?.let { findPODFlow.value = it }
        }
    }

    override suspend fun updatePODEntities(id: Int, favorite: Boolean) {
        inMemoryPod.value.find { it.id == id }.also { it?.favorite = favorite }

        if (::findPODFlow.isInitialized) {
            inMemoryPod.value.firstOrNull() { it.id == findPODFlow.value.id }
                ?.let { findPODFlow.value.favorite = favorite }
        }
    }

    override suspend fun insertEarthOnDb(items: List<DbEarth>) {
        inMemoryEarth.value = items

        if (::findEarthFlow.isInitialized) {
            items.firstOrNull() { it.id == findEarthFlow.value.id }
                ?.let { findEarthFlow.value = it }
        }
    }

    override suspend fun insertEarthEntities(items: List<DbEarth>) {
        inMemoryEarth.value = items

        if (::findEarthFlow.isInitialized) {
            items.firstOrNull() { it.id == findEarthFlow.value.id }
                ?.let { findEarthFlow.value = it }
        }
    }

    override suspend fun updateEarthEntities(id: Int, favorite: Boolean) {
        inMemoryEarth.value.find { it.id == id }.also { it?.favorite = favorite }

        if (::findEarthFlow.isInitialized) {
            inMemoryEarth.value.firstOrNull() { it.id == findEarthFlow.value.id }
                ?.let { findEarthFlow.value.favorite = favorite }
        }
    }

    override suspend fun insertMarsOnDb(items: List<DbMars>) {
        inMemoryMars.value = items

        if (::findMarsFlow.isInitialized) {
            items.firstOrNull() { it.id == findMarsFlow.value.id }
                ?.let { findMarsFlow.value = it }
        }
    }

    override suspend fun insertMarsEntities(items: List<DbMars>) {
        inMemoryMars.value = items

        if (::findMarsFlow.isInitialized) {
            items.firstOrNull() { it.id == findMarsFlow.value.id }
                ?.let { findMarsFlow.value = it }
        }
    }

    override suspend fun updateMarsEntities(id: Int, favorite: Boolean) {
        inMemoryMars.value.find { it.id == id }.also { it?.favorite = favorite }

        if (::findMarsFlow.isInitialized) {
            inMemoryEarth.value.firstOrNull() { it.id == findMarsFlow.value.id }
                ?.let { findMarsFlow.value.favorite = favorite }
        }
    }

}

// -- Fake Remotes Services --

class FakeRemotePODService(private val list: List<ApiAPOD> = emptyList()) : DailyPicturesService {

    override suspend fun getPictureOfTheDay(date: String): ApiAPOD {
        return if (list.isNotEmpty())
            list[0]
        else
            sampleRemotePOD
    }

    override suspend fun getPicturesOfDateRange(startDate: String, endDate: String): List<ApiAPOD> {
        return list
    }

}

val sampleRemotePOD = ApiAPOD(
    copyRight = "copyRight",
    date = "2022-05-16",
    title = "title",
    explanation = "explanation",
    media_type = "image",
    url = "url"
)

class FakeRemoteEarthService(private val list: List<ApiEPIC> = emptyList()) : DailyEarthService {

    override suspend fun getDailyEarthFromDate(date: String) =
        if (list.isNotEmpty()) list[0] else sampleRemoteEarth

    override suspend fun getDailyEarth() = list

}

val sampleRemoteEarth = ApiEPIC(
    identifier = "identifier",
    date = "2022-05-16",
    caption = "caption",
    image = "image"
)

class FakeRemoteMarsService(private val list: List<ApiMars> = emptyList()) : MarsService {

    override suspend fun getMarsGalleryFromSun(sol: Int) =
        if (list.isNotEmpty()) list[0] else sampleRemoteMars

    override suspend fun getMarsGalleryFromDate(date: String) =
        if (list.isNotEmpty()) list[0] else sampleRemoteMars

}

val sampleRemoteMars = ApiMars(
    photos = listOf(
        ApiMarsItem(
            id = 1,
            sol = 5000,
            img_src = "img_src",
            earth_date = "2022-05-16",
            camera = ApiMarsCamera(id = 2, name = "name", full_name = "full_name"),
            rover = ApiMarsRover(
                name = "name",
                landing_date = "2022-05-16",
                launching_date = "2022-05-16",
                status = "active"
            )
        )
    )
)
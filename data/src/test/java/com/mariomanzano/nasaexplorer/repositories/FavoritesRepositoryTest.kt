package com.mariomanzano.nasaexplorer.repositories

import com.devexperto.architectcoders.testshared.sampleEarth
import com.devexperto.architectcoders.testshared.sampleMars
import com.devexperto.architectcoders.testshared.samplePOD
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FavoritesRepositoryTest {

    @Mock
    lateinit var podLocalDataSource: PODLocalDataSource

    @Mock
    lateinit var earthLocalDataSource: EarthLocalDataSource

    @Mock
    lateinit var marsLocalDataSource: MarsLocalDataSource

    private lateinit var favoritesRepository: FavoritesRepository

    private val localPODList = flowOf(listOf(samplePOD.copy(1)))
    private val localEarthList = flowOf(listOf(sampleEarth.copy(1)))
    private val localMarsList = flowOf(listOf(sampleMars.copy(1)))

    @Before
    fun setUp() {
        whenever(podLocalDataSource.podListFavorite).thenReturn(localPODList)
        whenever(earthLocalDataSource.earthListFavorite).thenReturn(localEarthList)
        whenever(marsLocalDataSource.marsListFavorite).thenReturn(localMarsList)
        favoritesRepository =
            FavoritesRepository(podLocalDataSource, earthLocalDataSource, marsLocalDataSource)
    }

    @Test
    fun `Call getList returns a flow of favorite flows lists with exactly three flows`(): Unit =
        runBlocking {

            val result = favoritesRepository.getList()

            assertEquals(3, result.count())
        }

    @Test
    fun `Call findByIdAndType with dailyPicture type should call correct data source method`(): Unit =
        runBlocking {
            val pod = flowOf(samplePOD.copy(id = 5))
            whenever(podLocalDataSource.findPODById(5)).thenReturn(pod)

            val result = favoritesRepository.findByIdAndType(5, "dailyPicture")

            verify(podLocalDataSource).findPODById(any())
            assertEquals(pod, result)
        }

    @Test
    fun `Call findByIdAndType with earth type should call correct data source method`(): Unit =
        runBlocking {
            val earth = flowOf(sampleEarth.copy(id = 5))
            whenever(earthLocalDataSource.findEarthById(5)).thenReturn(earth)

            val result = favoritesRepository.findByIdAndType(5, "earth")

            verify(earthLocalDataSource).findEarthById(any())
            assertEquals(earth, result)
        }

    @Test
    fun `Call findByIdAndType with any param type should call by default to mars data source method`(): Unit =
        runBlocking {
            val mars = flowOf(sampleMars.copy(id = 5))
            whenever(marsLocalDataSource.findMarsById(5)).thenReturn(mars)

            val result = favoritesRepository.findByIdAndType(5, "")

            verify(marsLocalDataSource).findMarsById(any())
            assertEquals(mars, result)
        }

    @Test
    fun `Call switchFavorite with dailyPicture type should call the correct data source method`(): Unit =
        runBlocking {

            favoritesRepository.switchFavorite(5, "dailyPicture", false)

            verify(podLocalDataSource).updatePODList(5, false)
        }

    @Test
    fun `Call switchFavorite with earth type should call the correct data source method`(): Unit =
        runBlocking {

            favoritesRepository.switchFavorite(5, "earth", false)

            verify(earthLocalDataSource).updateEarthList(5, false)
        }

    @Test
    fun `Call switchFavorite with mars type should call the correct data source method`(): Unit =
        runBlocking {

            favoritesRepository.switchFavorite(5, "mars", false)

            verify(marsLocalDataSource).updateMarsList(5, false)
        }

    @Test
    fun `Call switchFavorite with other string type should not call to any data source`(): Unit =
        runBlocking {

            favoritesRepository.switchFavorite(5, "", false)

            verify(podLocalDataSource, never()).updatePODList(any(), any())
            verify(earthLocalDataSource, never()).updateEarthList(any(), any())
            verify(marsLocalDataSource, never()).updateMarsList(any(), any())
        }
}
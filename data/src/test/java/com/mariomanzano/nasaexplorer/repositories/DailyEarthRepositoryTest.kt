package com.mariomanzano.nasaexplorer.repositories

import arrow.core.right
import com.devexperto.architectcoders.testshared.sampleEarth
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.datasource.EarthLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.EarthRemoteDataSource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DailyEarthRepositoryTest {

    @Mock
    lateinit var earthLocalDataSource: EarthLocalDataSource

    @Mock
    lateinit var earthRemoteDataSource: EarthRemoteDataSource

    private lateinit var earthRepository: DailyEarthRepository

    private val localEarthList = flowOf(listOf(sampleEarth.copy(1)))

    @Before
    fun setUp() {
        whenever(earthLocalDataSource.earthList).thenReturn(localEarthList)
        earthRepository = DailyEarthRepository(earthLocalDataSource, earthRemoteDataSource)
    }

    @Test
    fun `Earth items are taken from local data source if available`(): Unit = runBlocking {

        val result = earthRepository.earthList

        assertEquals(localEarthList, result)
    }

    @Test
    fun `Pod items are saved to local data source when list come from the server`(): Unit =
        runBlocking {
            val remoteItems = listOf(sampleEarth.copy(2))
            whenever(earthRemoteDataSource.findEarthItems()).thenReturn(remoteItems.right())

            earthRepository.requestEarthList()

            verify(earthLocalDataSource).saveEarthList(remoteItems)
        }

    @Test
    fun `Error NoData is sended when empty list come from the server`(): Unit = runBlocking {
        val remoteItems = listOf(sampleEarth.copy(2))
        whenever(earthRemoteDataSource.findEarthItems()).thenReturn(emptyList<EarthItem>().right())

        val result = earthRepository.requestEarthList()

        verify(earthLocalDataSource, never()).saveEarthList(remoteItems)
        assertEquals(Error.NoData, result)
    }

    @Test
    fun `Finding a Earth of the Day by id is done in local data source`(): Unit = runBlocking {
        val earth = flowOf(sampleEarth.copy(id = 5))
        whenever(earthLocalDataSource.findEarthById(5)).thenReturn(earth)

        val result = earthRepository.findById(5)

        assertEquals(earth, result)
    }

    @Test
    fun `Switching favorite updates local data source`(): Unit = runBlocking {
        val element = sampleEarth.copy(id = 3)

        earthRepository.switchFavorite(element)

        verify(earthLocalDataSource).saveEarthFavoriteList(argThat { get(0).id == 3 })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite item`(): Unit = runBlocking {
        val element = sampleEarth.copy(favorite = false)

        earthRepository.switchFavorite(element)

        verify(earthLocalDataSource).saveEarthFavoriteList(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite item`(): Unit = runBlocking {
        val element = sampleEarth.copy(favorite = true)

        earthRepository.switchFavorite(element)

        verify(earthLocalDataSource).saveEarthFavoriteList(argThat { !get(0).favorite })
    }
}
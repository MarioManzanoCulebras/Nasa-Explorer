package com.mariomanzano.nasaexplorer.repositories

import arrow.core.left
import arrow.core.right
import com.devexperto.architectcoders.testshared.samplePOD
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.datasource.PODLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.PODRemoteDataSource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DailyPicturesRepositoryTest {

    @Mock
    lateinit var podLocalDataSource: PODLocalDataSource

    @Mock
    lateinit var podRemoteDataSource: PODRemoteDataSource

    private lateinit var dailyPicturesRepository: DailyPicturesRepository

    private val localPODList = flowOf(listOf(samplePOD.copy(1)))

    @Before
    fun setUp() {
        whenever(podLocalDataSource.podList).thenReturn(localPODList)
        dailyPicturesRepository = DailyPicturesRepository(podLocalDataSource, podRemoteDataSource)
    }

    @Test
    fun `Pod items are taken from local data source if available`(): Unit = runBlocking {

        val result = dailyPicturesRepository.podList

        assertEquals(localPODList, result)
    }

    @Test
    fun `Request to find Pod of the day not done if param Day is the same as one of the local list`(): Unit =
        runBlocking {
            dailyPicturesRepository.requestPODSingleDay(Calendar.getInstance())

            verify(podRemoteDataSource, never()).findPODDay(any())
            verify(podLocalDataSource, never()).savePODList(any())
        }

    @Test
    fun `Request to find Pod of the day is done if param Day is not at one of the local list and if request fail should not call to save`(): Unit =
        runBlocking {
            whenever(podRemoteDataSource.findPODDay(any())).thenReturn(Error.Connectivity.left())

            val result = dailyPicturesRepository.requestPODSingleDay(Calendar.getInstance().apply {
                set(
                    Calendar.YEAR,
                    Calendar.getInstance().get(Calendar.YEAR) - 1
                )
            })
            assertEquals(Error.Connectivity, result)
            verify(podLocalDataSource, never()).savePODList(any())
        }

    @Test
    fun `Request to find Pod of the day is done if param Day is not at one of the local list and its save on local`(): Unit =
        runBlocking {
            whenever(podRemoteDataSource.findPODDay(any())).thenReturn(samplePOD.right())

            dailyPicturesRepository.requestPODSingleDay(Calendar.getInstance().apply {
                set(
                    Calendar.YEAR,
                    Calendar.getInstance().get(Calendar.YEAR) - 1
                )
            })

            verify(podLocalDataSource).savePODList(any())
        }

    @Test
    fun `Pod items are saved to local data source when list come from the server`(): Unit =
        runBlocking {
            val remoteItems = listOf(samplePOD.copy(2))
            whenever(podRemoteDataSource.findPODitems(any(), any())).thenReturn(remoteItems.right())

            dailyPicturesRepository.requestPODList()

            verify(podLocalDataSource).savePODList(remoteItems)
        }

    @Test
    fun `Error NoData is sended when empty list come from the server`(): Unit = runBlocking {
        val remoteItems = listOf(samplePOD.copy(2))
        whenever(
            podRemoteDataSource.findPODitems(
                any(),
                any()
            )
        ).thenReturn(emptyList<PictureOfDayItem>().right())

        val result = dailyPicturesRepository.requestPODList()

        verify(podLocalDataSource, never()).savePODList(remoteItems)
        assertEquals(Error.NoData, result)
    }

    @Test
    fun `Finding a Picture of the Day by id is done in local data source`(): Unit = runBlocking {
        val pod = flowOf(samplePOD.copy(id = 5))
        whenever(podLocalDataSource.findPODById(5)).thenReturn(pod)

        val result = dailyPicturesRepository.findById(5)

        assertEquals(pod, result)
    }

    @Test
    fun `Switching favorite updates local data source`(): Unit = runBlocking {
        val element = samplePOD.copy(id = 3)

        dailyPicturesRepository.switchFavorite(element)

        verify(podLocalDataSource).savePODFavoriteList(argThat { get(0).id == 3 })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite item`(): Unit = runBlocking {
        val element = samplePOD.copy(favorite = false)

        dailyPicturesRepository.switchFavorite(element)

        verify(podLocalDataSource).savePODFavoriteList(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite item`(): Unit = runBlocking {
        val element = samplePOD.copy(favorite = true)

        dailyPicturesRepository.switchFavorite(element)

        verify(podLocalDataSource).savePODFavoriteList(argThat { !get(0).favorite })
    }
}
package com.mariomanzano.nasaexplorer.repositories

import arrow.core.right
import com.devexperto.architectcoders.testshared.sampleMars
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.datasource.MarsLocalDataSource
import com.mariomanzano.nasaexplorer.datasource.MarsRemoteDataSource
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
class MarsRepositoryTest {

    @Mock
    lateinit var marsLocalDataSource: MarsLocalDataSource

    @Mock
    lateinit var marsRemoteDataSource: MarsRemoteDataSource

    private lateinit var marsRepository: MarsRepository

    private val localMarsList = flowOf(listOf(sampleMars.copy(1)))

    @Before
    fun setUp() {
        whenever(marsLocalDataSource.marsList).thenReturn(localMarsList)
        marsRepository = MarsRepository(marsLocalDataSource, marsRemoteDataSource)
    }

    @Test
    fun `Mars items are taken from local data source if available`(): Unit = runBlocking {

        val result = marsRepository.marsList

        assertEquals(localMarsList, result)
    }

    @Test
    fun `Pod items are saved to local data source when list come from the server`(): Unit =
        runBlocking {
            val remoteItems = listOf(sampleMars.copy(2))
            whenever(marsRemoteDataSource.findMarsItems()).thenReturn(remoteItems.right())

            marsRepository.requestMarsList()

            verify(marsLocalDataSource).saveMarsList(remoteItems)
        }

    @Test
    fun `Error NoData is sended when empty list come from the server`(): Unit = runBlocking {
        val remoteItems = listOf(sampleMars.copy(2))
        whenever(marsRemoteDataSource.findMarsItems()).thenReturn(emptyList<MarsItem>().right())

        val result = marsRepository.requestMarsList()

        verify(marsLocalDataSource, never()).saveMarsList(remoteItems)
        assertEquals(Error.NoData, result)
    }

    @Test
    fun `Finding a Mars of the Day by id is done in local data source`(): Unit = runBlocking {
        val mars = flowOf(sampleMars.copy(id = 5))
        whenever(marsLocalDataSource.findMarsById(5)).thenReturn(mars)

        val result = marsRepository.findById(5)

        assertEquals(mars, result)
    }

    @Test
    fun `Switching favorite updates local data source`(): Unit = runBlocking {
        val element = sampleMars.copy(id = 3)

        marsRepository.switchFavorite(element)

        verify(marsLocalDataSource).saveMarsFavoriteList(argThat { get(0).id == 3 })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite item`(): Unit = runBlocking {
        val element = sampleMars.copy(favorite = false)

        marsRepository.switchFavorite(element)

        verify(marsLocalDataSource).saveMarsFavoriteList(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite item`(): Unit = runBlocking {
        val element = sampleMars.copy(favorite = true)

        marsRepository.switchFavorite(element)

        verify(marsLocalDataSource).saveMarsFavoriteList(argThat { !get(0).favorite })
    }
}
package com.mariomanzano.nasaexplorer.repositories

import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import com.mariomanzano.nasaexplorer.datasource.LastDbUpdateDataSource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class LastDbUpdateRepositoryTest {

    @Mock
    lateinit var lastDbUpdateDataSource: LastDbUpdateDataSource

    private lateinit var lastDbUpdateRepository: LastDbUpdateRepository

    private val localPODUpdateList = flowOf(sampleLastUpdateInfo.copy(1))
    private val localEarthUpdateList = flowOf(sampleLastUpdateInfo.copy(2))
    private val localMarsUpdateList = flowOf(sampleLastUpdateInfo.copy(3))

    @Before
    fun setUp() {
        whenever(lastDbUpdateDataSource.podTableUpdatedDay).thenReturn(localPODUpdateList)
        whenever(lastDbUpdateDataSource.earthTableUpdatedDay).thenReturn(localEarthUpdateList)
        whenever(lastDbUpdateDataSource.marsTableUpdatedDay).thenReturn(localMarsUpdateList)

        lastDbUpdateRepository = LastDbUpdateRepository(lastDbUpdateDataSource)
    }

    @Test
    fun `Last Pod Elements are taken from local data source if available`() {

        val result = lastDbUpdateRepository.lastPODUpdate

        assertEquals(localPODUpdateList, result)
    }

    @Test
    fun `Last Earth Elements are taken from local data source if available`() {

        val result = lastDbUpdateRepository.lastEarthUpdate

        assertEquals(localEarthUpdateList, result)
    }

    @Test
    fun `Last Mars Elements are taken from local data source if available`() {

        val result = lastDbUpdateRepository.lastMarsUpdate

        assertEquals(localMarsUpdateList, result)
    }

    @Test
    fun `Call updatePODLastUpdate should call update from the data source`(): Unit = runBlocking {
        val item = sampleLastUpdateInfo.copy(id = 20)

        lastDbUpdateRepository.updatePODLastUpdate(item)

        verify(lastDbUpdateDataSource).updatePODDate(item)
    }

    @Test
    fun `Call updateEarthLastUpdate should call update from the data source`(): Unit = runBlocking {
        val item = sampleLastUpdateInfo.copy(id = 20)

        lastDbUpdateRepository.updateEarthLastUpdate(item)

        verify(lastDbUpdateDataSource).updateEarthDate(item)
    }

    @Test
    fun `Call updateMarsLastUpdate should call update from the data source`(): Unit = runBlocking {
        val item = sampleLastUpdateInfo.copy(id = 20)

        lastDbUpdateRepository.updateMarsLastUpdate(item)

        verify(lastDbUpdateDataSource).updateMarsDate(item)
    }
}
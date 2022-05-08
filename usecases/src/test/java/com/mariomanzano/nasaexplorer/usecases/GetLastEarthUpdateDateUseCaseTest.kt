package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetLastEarthUpdateDateUseCaseTest {

    @Test
    fun `Invoke calls element Earth repository`(): Unit = runBlocking {
        val element = flowOf(sampleLastUpdateInfo.copy(id = 1))
        val getLastEarthUpdateDateUseCase = GetLastEarthUpdateDateUseCase(mock {
            on { lastEarthUpdate } doReturn (element)
        })

        val result = getLastEarthUpdateDateUseCase()

        assertEquals(element, result)
    }
}
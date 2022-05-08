package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleEarth
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetEarthUseCaseTest {

    @Test
    fun `Invoke calls element Earth repository`(): Unit = runBlocking {
        val list = flowOf(listOf(sampleEarth.copy(id = 1)))
        val getEarthUseCase = GetEarthUseCase(mock {
            on { earthList } doReturn (list)
        })

        val result = getEarthUseCase()

        assertEquals(list, result)
    }
}
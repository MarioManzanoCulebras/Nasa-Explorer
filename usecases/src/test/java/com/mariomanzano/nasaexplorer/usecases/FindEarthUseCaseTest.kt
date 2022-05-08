package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleEarth
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindEarthUseCaseTest {

    @Test
    fun `Invoke calls element Earth repository`(): Unit = runBlocking {
        val element = flowOf(sampleEarth.copy(id = 1))
        val findEarthUseCase = FindEarthUseCase(mock {
            on { findById(1) } doReturn (element)
        })

        val result = findEarthUseCase(1)

        assertEquals(element, result)
    }
}
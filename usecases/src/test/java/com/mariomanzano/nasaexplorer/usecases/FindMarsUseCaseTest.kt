package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleMars
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindMarsUseCaseTest {

    @Test
    fun `Invoke calls element Mars repository`(): Unit = runBlocking {
        val element = flowOf(sampleMars.copy(id = 1))
        val findMarsUseCase = FindMarsUseCase(mock {
            on { findById(1) } doReturn (element)
        })

        val result = findMarsUseCase(1)

        assertEquals(element, result)
    }
}
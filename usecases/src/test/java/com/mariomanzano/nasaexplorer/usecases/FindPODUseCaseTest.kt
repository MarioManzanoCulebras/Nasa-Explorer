package com.mariomanzano.nasaexplorer.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindPODUseCaseTest {

    @Test
    fun `Invoke calls element POD repository`(): Unit = runBlocking {
        val element = flowOf(samplePOD.copy(id = 1))
        val findPODUseCase = FindPODUseCase(mock {
            on { findById(1) } doReturn (element)
        })

        val result = findPODUseCase(1)

        assertEquals(element, result)
    }
}
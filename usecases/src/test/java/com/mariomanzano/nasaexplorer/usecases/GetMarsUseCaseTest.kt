package com.mariomanzano.nasaexplorer.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetMarsUseCaseTest {

    @Test
    fun `Invoke calls element Mars repository`(): Unit = runBlocking {
        val list = flowOf(listOf(sampleMars.copy(id = 1)))
        val getMarsUseCase = GetMarsUseCase(mock {
            on { marsList } doReturn (list)
        })

        val result = getMarsUseCase()

        assertEquals(list, result)
    }
}
package com.mariomanzano.nasaexplorer.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetLastPODUpdateDateUseCaseTest {

    @Test
    fun `Invoke calls element POD repository`(): Unit = runBlocking {
        val element = flowOf(sampleLastUpdateInfo.copy(id = 1))
        val getLastPODUpdateDateUseCase = GetLastPODUpdateDateUseCase(mock {
            on { lastPODUpdate } doReturn (element)
        })

        val result = getLastPODUpdateDateUseCase()

        assertEquals(element, result)
    }
}
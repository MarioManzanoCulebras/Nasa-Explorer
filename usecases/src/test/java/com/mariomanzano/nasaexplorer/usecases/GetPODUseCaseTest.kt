package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.samplePOD
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetPODUseCaseTest {

    @Test
    fun `Invoke calls element POD repository`(): Unit = runBlocking {
        val list = flowOf(listOf(samplePOD.copy(id = 1)))
        val getPODUseCase = GetPODUseCase(mock {
            on { podList } doReturn (list)
        })

        val result = getPODUseCase()

        assertEquals(list, result)
    }
}
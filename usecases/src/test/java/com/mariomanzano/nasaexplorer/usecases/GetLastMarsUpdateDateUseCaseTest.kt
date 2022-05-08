package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetLastMarsUpdateDateUseCaseTest {

    @Test
    fun `Invoke calls element Mars repository`(): Unit = runBlocking {
        val element = flowOf(sampleLastUpdateInfo.copy(id = 1))
        val getLastMarsUpdateDateUseCase = GetLastMarsUpdateDateUseCase(mock {
            on { lastMarsUpdate } doReturn (element)
        })

        val result = getLastMarsUpdateDateUseCase()

        assertEquals(element, result)
    }
}
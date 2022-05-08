package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleEarth
import com.devexperto.architectcoders.testshared.sampleMars
import com.devexperto.architectcoders.testshared.samplePOD
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetFavoritesUseCaseTest {

    @Test
    fun `Invoke calls list favorites repository`(): Unit = runBlocking {
        val list = flowOf(
            flowOf(
                listOf(
                    samplePOD.copy(id = 1),
                    sampleEarth.copy(id = 1),
                    sampleMars.copy(id = 1)
                )
            )
        )
        val getFavoritesUseCase = GetFavoritesUseCase(mock {
            on { getList() } doReturn (list)
        })

        val result = getFavoritesUseCase()

        assertEquals(list, result)
    }
}
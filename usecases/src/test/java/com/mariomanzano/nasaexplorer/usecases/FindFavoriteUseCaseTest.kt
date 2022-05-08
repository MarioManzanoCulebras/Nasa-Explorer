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

class FindFavoriteUseCaseTest {

    @Test
    fun `Invoke calls element favorite as POD type from repository`(): Unit = runBlocking {
        val element = flowOf(samplePOD.copy(id = 1))
        val findFavoriteUseCase = FindFavoriteUseCase(mock {
            on { findByIdAndType(1, "dailyPicture") } doReturn (element)
        })

        val result = findFavoriteUseCase(1, "dailyPicture")

        assertEquals(element, result)
    }

    @Test
    fun `Invoke calls element favorite as Earth type from repository`(): Unit = runBlocking {
        val element = flowOf(sampleEarth.copy(id = 1))
        val findFavoriteUseCase = FindFavoriteUseCase(mock {
            on { findByIdAndType(1, "earth") } doReturn (element)
        })

        val result = findFavoriteUseCase(1, "earth")

        assertEquals(element, result)
    }

    @Test
    fun `Invoke calls element favorite as Mars type from repository`(): Unit = runBlocking {
        val element = flowOf(sampleMars.copy(id = 1))
        val findFavoriteUseCase = FindFavoriteUseCase(mock {
            on { findByIdAndType(1, "mars") } doReturn (element)
        })

        val result = findFavoriteUseCase(1, "mars")

        assertEquals(element, result)
    }
}
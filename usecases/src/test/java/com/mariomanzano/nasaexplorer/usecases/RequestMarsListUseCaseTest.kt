package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestMarsListUseCaseTest {

    @Test
    fun `Invoke calls item repository`(): Unit = runBlocking {
        val marsRepository = mock<MarsRepository>()
        val requestMarsListUseCase = RequestMarsListUseCase(marsRepository)

        requestMarsListUseCase()

        verify(marsRepository).requestMarsList()
    }
}
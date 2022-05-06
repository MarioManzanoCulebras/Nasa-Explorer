package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestPODListUseCaseTest {

    @Test
    fun `Invoke calls item repository`(): Unit = runBlocking {
        val dailyPicturesRepository = mock<DailyPicturesRepository>()
        val requestPODListUseCase = RequestPODListUseCase(dailyPicturesRepository)

        requestPODListUseCase()

        verify(dailyPicturesRepository).requestPODList()
    }
}
package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestEarthListUseCaseTest {

    @Test
    fun `Invoke calls item repository`(): Unit = runBlocking {
        val dailyEarthRepository = mock<DailyEarthRepository>()
        val requestEarthListUseCase = RequestEarthListUseCase(dailyEarthRepository)

        requestEarthListUseCase()

        verify(dailyEarthRepository).requestEarthList()
    }
}
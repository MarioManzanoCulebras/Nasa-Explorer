package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.*

class RequestPODSingleDayListUseCaseTest {

    @Test
    fun `Invoke calls item repository`(): Unit = runBlocking {
        val dailyPicturesRepository = mock<DailyPicturesRepository>()
        val requestPODSingleDayUseCase = RequestPODSingleDayUseCase(dailyPicturesRepository)
        val date = Calendar.getInstance()

        requestPODSingleDayUseCase(date)

        verify(dailyPicturesRepository).requestPODSingleDay(date)
    }
}
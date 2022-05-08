package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateLastEarthUpdateUseCaseTest {

    @Test
    fun `Invoke calls lastUpdateInfo repository`(): Unit = runBlocking {
        val lastDbUpdateRepository = mock<LastDbUpdateRepository>()
        val updateLastEarthUpdateUseCase = UpdateLastEarthUpdateUseCase(lastDbUpdateRepository)
        val lastUpdateInfo = sampleLastUpdateInfo

        updateLastEarthUpdateUseCase(lastUpdateInfo)

        verify(lastDbUpdateRepository).updateEarthLastUpdate(lastUpdateInfo)
    }
}
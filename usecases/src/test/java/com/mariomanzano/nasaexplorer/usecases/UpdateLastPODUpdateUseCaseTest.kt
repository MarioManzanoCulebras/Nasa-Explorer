package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateLastPODUpdateUseCaseTest {

    @Test
    fun `Invoke calls lastUpdateInfo repository`(): Unit = runBlocking {
        val lastDbUpdateRepository = mock<LastDbUpdateRepository>()
        val updateLastPODUpdateUseCase = UpdateLastPODUpdateUseCase(lastDbUpdateRepository)
        val lastUpdateInfo = sampleLastUpdateInfo

        updateLastPODUpdateUseCase(lastUpdateInfo)

        verify(lastDbUpdateRepository).updatePODLastUpdate(lastUpdateInfo)
    }
}
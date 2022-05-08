package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UpdateLastMarsUpdateUseCaseTest {

    @Test
    fun `Invoke calls lastUpdateInfo repository`(): Unit = runBlocking {
        val lastDbUpdateRepository = mock<LastDbUpdateRepository>()
        val updateLastMarsUpdateUseCase = UpdateLastMarsUpdateUseCase(lastDbUpdateRepository)
        val lastUpdateInfo = sampleLastUpdateInfo

        updateLastMarsUpdateUseCase(lastUpdateInfo)

        verify(lastDbUpdateRepository).updateMarsLastUpdate(lastUpdateInfo)
    }
}
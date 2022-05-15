package com.mariomanzano.nasaexplorer.ui

import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.usecases.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getLastPODUpdateDateUseCase: GetLastPODUpdateDateUseCase

    @Mock
    lateinit var getLastEarthUpdateDateUseCase: GetLastEarthUpdateDateUseCase

    @Mock
    lateinit var getLastMarsUpdateDateUseCase: GetLastMarsUpdateDateUseCase

    @Mock
    lateinit var updateLastPODUpdateUseCase: UpdateLastPODUpdateUseCase

    @Mock
    lateinit var updateLastEarthUpdateUseCase: UpdateLastEarthUpdateUseCase

    @Mock
    lateinit var updateLastMarsUpdateUseCase: UpdateLastMarsUpdateUseCase


    private lateinit var vm: MainViewModel

    @Before
    fun setup() {
        vm = MainViewModel(
            getLastPODUpdateDateUseCase,
            getLastEarthUpdateDateUseCase,
            getLastMarsUpdateDateUseCase,
            updateLastPODUpdateUseCase,
            updateLastEarthUpdateUseCase,
            updateLastMarsUpdateUseCase
        )
    }

    @Test
    fun `Use case for updating is called to update lastUpdate info date if last update was yesterday`() =
        runTest {
            //Simulate last update was yesterday
            val lastPodUpdatedYesterday =
                sampleLastUpdateInfo.copy(date = Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_MONTH, this.get(Calendar.DAY_OF_MONTH) - 1)
                })
            val lastEarthUpdatedYesterday =
                sampleLastUpdateInfo.copy(date = Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_MONTH, this.get(Calendar.DAY_OF_MONTH) - 1)
                })
            val lastMarsUpdatedYesterday =
                sampleLastUpdateInfo.copy(date = Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_MONTH, this.get(Calendar.DAY_OF_MONTH) - 1)
                })
            whenever(getLastPODUpdateDateUseCase()).thenReturn(flowOf(lastPodUpdatedYesterday))
            whenever(getLastEarthUpdateDateUseCase()).thenReturn(flowOf(lastEarthUpdatedYesterday))
            whenever(getLastMarsUpdateDateUseCase()).thenReturn(flowOf(lastMarsUpdatedYesterday))

            vm.updateDates()
            runCurrent()

            verify(updateLastPODUpdateUseCase).invoke(argThat { updateNeed })
            verify(updateLastEarthUpdateUseCase).invoke(argThat { updateNeed })
            verify(updateLastMarsUpdateUseCase).invoke(argThat { updateNeed })
        }

    @Test
    fun `Use case for updating is not called if it is the same day`() = runTest {
        whenever(getLastPODUpdateDateUseCase()).thenReturn(flowOf(sampleLastUpdateInfo))
        whenever(getLastEarthUpdateDateUseCase()).thenReturn(flowOf(sampleLastUpdateInfo))
        whenever(getLastMarsUpdateDateUseCase()).thenReturn(flowOf(sampleLastUpdateInfo))

        vm.updateDates()
        runCurrent()

        verify(updateLastPODUpdateUseCase, never()).invoke(any())
        verify(updateLastEarthUpdateUseCase, never()).invoke(any())
        verify(updateLastMarsUpdateUseCase, never()).invoke(any())
    }

}
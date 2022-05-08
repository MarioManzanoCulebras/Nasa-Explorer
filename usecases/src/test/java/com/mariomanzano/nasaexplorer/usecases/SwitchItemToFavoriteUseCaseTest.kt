package com.mariomanzano.nasaexplorer.usecases

import com.devexperto.architectcoders.testshared.sampleEarth
import com.devexperto.architectcoders.testshared.sampleMars
import com.devexperto.architectcoders.testshared.samplePOD
import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SwitchItemToFavoriteUseCaseTest {

    @Mock
    lateinit var dailyPicturesRepository: DailyPicturesRepository

    @Mock
    lateinit var dailyEarthRepository: DailyEarthRepository

    @Mock
    lateinit var marsRepository: MarsRepository

    private lateinit var switchItemToFavoriteUseCase: SwitchItemToFavoriteUseCase

    @Before
    fun setUp() {
        switchItemToFavoriteUseCase = SwitchItemToFavoriteUseCase(
            dailyPicturesRepository,
            dailyEarthRepository,
            marsRepository
        )
    }

    @Test
    fun `Invoke calls POD repository`(): Unit = runBlocking {
        val dailyPictureItem = samplePOD.copy(id = 1)

        switchItemToFavoriteUseCase(dailyPictureItem)

        verify(dailyPicturesRepository, times(1)).switchFavorite(dailyPictureItem)
    }

    @Test
    fun `Invoke calls Earth repository`(): Unit = runBlocking {
        val earthItem = sampleEarth.copy(id = 1)

        switchItemToFavoriteUseCase(earthItem)

        verify(dailyEarthRepository, times(1)).switchFavorite(earthItem)
    }

    @Test
    fun `Invoke calls Mars repository`(): Unit = runBlocking {
        val marsItem = sampleMars.copy(id = 1)

        switchItemToFavoriteUseCase(marsItem)

        verify(marsRepository, times(1)).switchFavorite(marsItem)
    }
}
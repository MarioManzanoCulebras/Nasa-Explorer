package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SwitchItemToFavoriteUseCaseTest {

    @Test
    fun `Invoke calls POD repository`(): Unit = runBlocking {
        val dailyPictureItem = samplePOD.copy(id = 1)
        val dailyPicturesRepository = mock<DailyPicturesRepository>()
        val dailyEarthRepository = mock<DailyEarthRepository>()
        val marsRepository = mock<MarsRepository>()
        val switchItemToFavoriteUseCase =
            SwitchItemToFavoriteUseCase(
                dailyPicturesRepository,
                dailyEarthRepository,
                marsRepository
            )

        switchItemToFavoriteUseCase(dailyPictureItem)

        verify(dailyPicturesRepository, times(1)).switchFavorite(dailyPictureItem)
    }

    @Test
    fun `Invoke calls Earth repository`(): Unit = runBlocking {
        val earthItem = sampleEarth.copy(id = 1)
        val dailyPicturesRepository = mock<DailyPicturesRepository>()
        val dailyEarthRepository = mock<DailyEarthRepository>()
        val marsRepository = mock<MarsRepository>()
        val switchItemToFavoriteUseCase =
            SwitchItemToFavoriteUseCase(
                dailyPicturesRepository,
                dailyEarthRepository,
                marsRepository
            )

        switchItemToFavoriteUseCase(earthItem)

        verify(dailyEarthRepository, times(1)).switchFavorite(earthItem)
    }

    @Test
    fun `Invoke calls Mars repository`(): Unit = runBlocking {
        val marsItem = sampleMars.copy(id = 1)
        val dailyPicturesRepository = mock<DailyPicturesRepository>()
        val dailyEarthRepository = mock<DailyEarthRepository>()
        val marsRepository = mock<MarsRepository>()
        val switchItemToFavoriteUseCase =
            SwitchItemToFavoriteUseCase(
                dailyPicturesRepository,
                dailyEarthRepository,
                marsRepository
            )

        switchItemToFavoriteUseCase(marsItem)

        verify(marsRepository, times(1)).switchFavorite(marsItem)
    }
}
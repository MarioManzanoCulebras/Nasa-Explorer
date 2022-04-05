package com.mariomanzano.nasaexplorer.network

import arrow.core.Either
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.datasource.EarthRemoteDataSource
import com.mariomanzano.nasaexplorer.network.entities.asEarthItem
import javax.inject.Inject

class EarthServerDataSource @Inject constructor(
    private val dailyEarthService: DailyEarthService
) : EarthRemoteDataSource {

    override suspend fun findEarthItems(): Either<Error, List<EarthItem>> =
        tryCall {
            dailyEarthService
                .getDailyEarth()
                .map { it.asEarthItem() }
                .sortedByDescending { it.date }
        }
}
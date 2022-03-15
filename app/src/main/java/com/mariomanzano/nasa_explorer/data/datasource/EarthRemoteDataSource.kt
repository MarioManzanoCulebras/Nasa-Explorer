package com.mariomanzano.nasa_explorer.data.datasource

import arrow.core.Either
import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.Error

interface EarthRemoteDataSource {
    suspend fun findEarthItems(): Either<Error, List<EarthItem>>
}
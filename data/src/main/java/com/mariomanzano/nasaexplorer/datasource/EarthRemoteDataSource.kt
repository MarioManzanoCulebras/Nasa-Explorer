package com.mariomanzano.nasaexplorer.datasource

import arrow.core.Either
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem

interface EarthRemoteDataSource {
    suspend fun findEarthItems(): Either<Error, List<EarthItem>>
}
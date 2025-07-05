package com.klivver.cities.usecases.loadCities

import com.klivver.cities.repositories.ICityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale


class LoadCitiesUseCase(
    private val repository: ICityRepository
) : ILoadCitiesUseCase {
    override suspend operator fun invoke() = withContext(Dispatchers.Default) {
        repository.loadCities().sortedWith(
            compareBy({ it.name.lowercase(Locale.ROOT) }, { it.country.lowercase(Locale.ROOT) })
        )
    }

}


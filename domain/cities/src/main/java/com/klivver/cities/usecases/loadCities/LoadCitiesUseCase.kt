package com.klivver.cities.usecases.loadCities

import com.klivver.cities.repositories.ICityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Use case for loading and sorting all cities from the repository.
 *
 * This class encapsulates the logic for retrieving cities from the [ICityRepository]
 * and sorting them alphabetically by name and country code.
 *
 * Runs on [Dispatchers.Default] to ensure that heavy computation
 * (such as sorting large lists) is done off the main thread.
 *
 * @property repository the city repository providing access to city data
 */
class LoadCitiesUseCase(
    private val repository: ICityRepository
) : ILoadCitiesUseCase {
    /**
     * Loads all cities from the repository and returns them sorted by name and country.
     *
     * @return a sorted list of [City] objects
     */
    override suspend operator fun invoke() = withContext(Dispatchers.Default) {
        repository.loadCities().sortedWith(
            compareBy({ it.name.lowercase(Locale.ROOT) }, { it.country.lowercase(Locale.ROOT) })
        )
    }

}


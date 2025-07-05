package com.klivver.cities.usecases.searchForCities

import com.klivver.cities.repositories.ICityRepository
import com.klivver.cities.models.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Use case for searching cities by a given prefix.
 *
 * This class encapsulates the search logic, delegating to [ICityRepository],
 * and ensures results are sorted alphabetically by city name and country code.
 *
 * Runs on [Dispatchers.Default] to ensure the operation is off the main thread,
 * which is important for large data sets (~200K+ cities).
 *
 * @property repository the repository that provides access to city search
 */
class SearchCitiesByPrefixUseCase(
    private val repository: ICityRepository
) : ISearchCitiesByPrefixUseCase {

    /**
     * Searches for cities whose names start with the given [prefix].
     * Results are sorted by name and country code, both in a case-insensitive manner.
     *
     * @param prefix the query string used to filter cities by name
     * @return a sorted list of [City]s matching the prefix
     */
    override suspend operator fun invoke(prefix: String): List<City> =
        withContext(Dispatchers.Default) {
            repository.searchCitiesByPrefix(prefix)
                .sortedWith(
                    compareBy(
                        { it.name.lowercase(Locale.ROOT) },
                        { it.country.lowercase(Locale.ROOT) }
                    )
                )
        }
}



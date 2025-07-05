package com.klivver.cities.usecases.searchForCities

import com.klivver.cities.repositories.ICityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchCitiesByPrefixUseCase(
    private val repository: ICityRepository
) : ISearchCitiesByPrefixUseCase {
    override suspend operator fun invoke(prefix: String) =
        withContext(Dispatchers.Default){ repository.searchCitiesByPrefix(prefix).sortedWith(compareBy({ it.name.lowercase() }, { it.country.lowercase() }))}

}


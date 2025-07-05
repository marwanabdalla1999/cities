package com.klivver.cities.usecases.searchForCities

import com.klivver.cities.models.City

interface ISearchCitiesByPrefixUseCase {
    suspend operator fun invoke(prefix: String): List<City>
} 
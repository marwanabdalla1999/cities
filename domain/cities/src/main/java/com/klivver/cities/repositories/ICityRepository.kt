package com.klivver.cities.repositories

import com.klivver.cities.models.City

/**
 * Repository exposing city data to the domain layer, with sorting and mapping.
 */
interface ICityRepository {
    /**
     * Loads and returns the full list of cities, sorted alphabetically by name and country.
     */
    suspend fun loadCities(): List<City>

    /**
     * Searches for cities with names that start with [prefix], case-insensitive.
     * Returns sorted results.
     */
    suspend fun searchCitiesByPrefix(prefix: String): List<City>
}
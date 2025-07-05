package com.klivver.cities.dataSource

import com.klivver.cities.models.CityDto

interface ICityLocalDataSource {
    /** Loads + builds the Trie once; subsequent calls just return the cached list. */
    suspend fun preloadCities()

    /** Returns all cities from the Trie as a List. */
    fun getAllCities(): List<CityDto>

    /** Returns only the prefix‐matched cities from the Trie. */
    suspend fun searchCitiesByPrefix(prefix: String): List<CityDto>
}

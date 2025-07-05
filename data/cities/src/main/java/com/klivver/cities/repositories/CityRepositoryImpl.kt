package com.klivver.cities.repositories

import com.klivver.cities.dataSource.ICityLocalDataSource
import javax.inject.Inject
import com.klivver.cities.models.City
import com.klivver.cities.mappers.CityMapper.toDomain

/**
 * Implementation of [ICityRepository] that provides access to city data.
 *
 * This repository acts as a bridge between the UI/domain layer and the in-memory
 * data source. It ensures data is preloaded before use and transforms raw [CityDto]
 * objects into domain-layer [City] models.
 *
 * All operations are suspendable to allow off-main-thread execution and to integrate
 * easily with coroutines and reactive flows.
 *
 * @property localDataSource the underlying data source that holds and manages in-memory city data
 */

class CityRepositoryImpl @Inject constructor(
    private val localDataSource: ICityLocalDataSource
) : ICityRepository {


    override suspend fun loadCities(): List<City>  {
        localDataSource.preloadCities()
        val sortedCities = localDataSource.getAllCities().map { it.toDomain() }
        return sortedCities
    }

    override suspend fun searchCitiesByPrefix(prefix: String): List<City> {
        // You can preload if not already done, to ensure list is built
        localDataSource.preloadCities()
        // Perform prefix search, then sort & map
        return localDataSource.searchCitiesByPrefix(prefix)
            .map { it.toDomain() }
    }
}

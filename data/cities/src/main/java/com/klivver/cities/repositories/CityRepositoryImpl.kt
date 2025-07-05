package com.klivver.cities.repositories

import com.klivver.cities.dataSource.ICityLocalDataSource
import javax.inject.Inject
import com.klivver.cities.models.City
import com.klivver.cities.mappers.CityMapper.toDomain



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

package com.klivver.cities.usecases.loadCities

import com.klivver.cities.models.City

interface ILoadCitiesUseCase {
    suspend operator fun invoke(): List<City>
} 
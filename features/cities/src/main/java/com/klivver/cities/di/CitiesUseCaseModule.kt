package com.klivver.cities.di

import com.klivver.cities.repositories.ICityRepository
import com.klivver.cities.usecases.loadCities.ILoadCitiesUseCase
import com.klivver.cities.usecases.loadCities.LoadCitiesUseCase
import com.klivver.cities.usecases.searchForCities.ISearchCitiesByPrefixUseCase
import com.klivver.cities.usecases.searchForCities.SearchCitiesByPrefixUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CitiesUseCaseModule {

    @Provides
    fun provideLoadCitiesUseCase(
        cityRepository: ICityRepository
    ): ILoadCitiesUseCase = LoadCitiesUseCase(cityRepository)

    @Provides
    fun provideSearchCitiesByPrefixUseCase(
        cityRepository: ICityRepository
    ): ISearchCitiesByPrefixUseCase = SearchCitiesByPrefixUseCase(cityRepository)
} 
package com.klivver.cities.di

import android.content.Context
import com.klivver.cities.dataSource.CityLocalDataSourceImpl
import com.klivver.cities.dataSource.ICityLocalDataSource
import com.klivver.cities.repositories.CityRepositoryImpl
import com.klivver.cities.repositories.ICityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CityRepositoryModule {

    @Provides
    @Singleton
     fun bindCityRepository(
       cityLocalDataSource : ICityLocalDataSource
    ): ICityRepository = CityRepositoryImpl(cityLocalDataSource)


    @Provides
    @Singleton
     fun bindCityDataSource(
        @ApplicationContext context: Context
    ): ICityLocalDataSource = CityLocalDataSourceImpl(context)



}
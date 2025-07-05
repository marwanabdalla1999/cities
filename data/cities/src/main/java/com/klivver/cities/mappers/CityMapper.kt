package com.klivver.cities.mappers

import com.klivver.cities.models.CityDto
import com.klivver.cities.models.City

object CityMapper {
    fun CityDto.toDomain(): City = City(
        id = id,
        name = name,
        country = country,
        lat =coord.lat,
        lon = coord.lon
    )
}
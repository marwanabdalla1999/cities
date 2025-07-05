package com.klivver.cities.uiContract

import com.klivver.cities.models.City
import com.klivver.ui.bases.ViewEvent
import com.klivver.ui.bases.ViewSideEffect
import com.klivver.ui.bases.ViewState

data class CitiesViewState(
    val isLoading: Boolean = true,
    val cities: List<City> = emptyList(),
    val query: String = ""
) : ViewState

sealed class CitiesViewEvent : ViewEvent {
    data class Search(val prefix: String) : CitiesViewEvent()
    data class CitySelected(val city: City) : CitiesViewEvent()
    data object LoadAll : CitiesViewEvent()
}

sealed class CitiesSideEffect: ViewSideEffect {
    data class ShowError(val message: String) : CitiesSideEffect()
    data class OpenMap(val lat: Double, val lon: Double) : CitiesSideEffect()
} 
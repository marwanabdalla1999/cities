package com.klivver.cities.uiContract

import com.klivver.cities.models.City
import com.klivver.ui.bases.ViewEvent
import com.klivver.ui.bases.ViewSideEffect
import com.klivver.ui.bases.ViewState

/**
 * Represents the UI state of the City Search screen.
 *
 * @property isLoading indicates whether data is currently being loaded
 * @property cities the list of cities to be displayed
 * @property query the current search query entered by the user
 */
data class CitiesViewState(
    val isLoading: Boolean = true,
    val cities: List<City> = emptyList(),
    val query: String = ""
) : ViewState

/**
 * Defines all possible user-driven events for the City Search screen.
 */
sealed class CitiesViewEvent : ViewEvent {

    /**
     * Triggered when the user types in the search bar.
     *
     * @param prefix the string to search for in city names
     */
    data class Search(val prefix: String) : CitiesViewEvent()

    /**
     * Triggered when the user selects a city from the list.
     *
     * @param city the selected city
     */
    data class CitySelected(val city: City) : CitiesViewEvent()

    /**
     * Triggered once during screen start to load all cities.
     */
    data object LoadAll : CitiesViewEvent()
}

/**
 * Represents one-time UI side effects such as navigation or displaying messages.
 */
sealed class CitiesSideEffect : ViewSideEffect {

    /**
     * Emits an error message to be shown in a Snackbar or dialog.
     *
     * @param message the message to display
     */
    data class ShowError(val message: String) : CitiesSideEffect()

    /**
     * Triggers a map intent to open the selected city in Google Maps.
     *
     * @param lat the latitude of the selected city
     * @param lon the longitude of the selected city
     */
    data class OpenMap(val lat: Double, val lon: Double) : CitiesSideEffect()
}

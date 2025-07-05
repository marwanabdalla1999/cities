package com.klivver.cities

import com.klivver.cities.uiContract.CitiesViewState
import com.klivver.cities.uiContract.CitiesViewEvent
import com.klivver.cities.uiContract.CitiesSideEffect
import com.klivver.cities.models.City
import com.klivver.cities.usecases.loadCities.ILoadCitiesUseCase
import com.klivver.cities.usecases.searchForCities.ISearchCitiesByPrefixUseCase
import com.klivver.ui.bases.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

/**
 * ViewModel for managing city search and interaction logic in the City Search screen.
 *
 * This class exposes a [CitiesViewState] for the UI to observe, and processes [CitiesViewEvent]
 * to perform actions such as loading all cities, searching by prefix, or opening a selected city on the map.
 * It emits one-time [CitiesSideEffect]s such as errors or map launch triggers.
 *
 * @property loadCitiesUseCase use case for loading all cities initially
 * @property searchCitiesByPrefixUseCase use case for performing prefix-based city search
 */
@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val loadCitiesUseCase: ILoadCitiesUseCase,
    private val searchCitiesByPrefixUseCase: ISearchCitiesByPrefixUseCase
) : BaseViewModel<CitiesViewState, CitiesViewEvent, CitiesSideEffect>() {

    private var searchJob: Job? = null

    init {
        // Load all cities immediately when the ViewModel is initialized
        handleEvents(CitiesViewEvent.LoadAll)
    }

    /**
     * Loads all cities using the injected use case and updates the view state.
     */
    private fun loadAllCities() {
        viewModelScope.launch {
            try {
                val city = loadCitiesUseCase()
                setState { copy(isLoading = false, cities = city) }
            } catch (e: Exception) {
                setEffect { CitiesSideEffect.ShowError(e.message ?: "Unknown error") }
            }
        }
    }

    /**
     * Performs a debounced search for cities based on the given [prefix].
     * Cancels any ongoing search to avoid overlap.
     *
     * @param prefix the query string entered by the user
     */
    private fun searchCities(prefix: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            setState { copy(isLoading = true) }

            delay(500) // debounce to reduce redundant searches

            try {
                val result = searchCitiesByPrefixUseCase(prefix)
                setState {
                    copy(
                        isLoading = false,
                        cities = result
                    )
                }
            } catch (e: CancellationException) {
                throw e // respect coroutine cancellation
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect { CitiesSideEffect.ShowError(e.message ?: "Unknown error") }
            }
        }
    }

    /**
     * Emits a side effect to open a map application at the coordinates of the selected [city].
     */
    private fun openMap(city: City) {
        viewModelScope.launch {
            setEffect { CitiesSideEffect.OpenMap(city.lat, city.lon) }
        }
    }

    /**
     * Returns the initial state of the view.
     */
    override fun setInitialState(): CitiesViewState = CitiesViewState()

    /**
     * Handles UI events and dispatches the appropriate logic.
     *
     * @param event the event triggered by the user interface
     */
    override fun handleEvents(event: CitiesViewEvent) {
        when (event) {
            is CitiesViewEvent.LoadAll -> loadAllCities()

            is CitiesViewEvent.Search -> {
                setState { copy(query = event.prefix) }
                searchCities(event.prefix)
            }

            is CitiesViewEvent.CitySelected -> openMap(event.city)
        }
    }
}

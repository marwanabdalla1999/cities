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


@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val loadCitiesUseCase: ILoadCitiesUseCase,
    private val searchCitiesByPrefixUseCase: ISearchCitiesByPrefixUseCase
) : BaseViewModel<CitiesViewState, CitiesViewEvent, CitiesSideEffect>() {
    private var searchJob: Job? = null

    init {
        handleEvents(CitiesViewEvent.LoadAll)
    }

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

    private fun searchCities(prefix: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            setState { copy(isLoading = true) }

            delay(500) // debounce

            try {
                val result = searchCitiesByPrefixUseCase(prefix)
                setState {
                    copy(
                        isLoading = false,
                        cities = result
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect { CitiesSideEffect.ShowError(e.message ?: "Unknown error") }
            }
        }
    }

    private fun openMap(city: City) {
        viewModelScope.launch {
            setEffect { CitiesSideEffect.OpenMap(city.lat, city.lon) }
        }
    }

    override fun setInitialState(): CitiesViewState = CitiesViewState()

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
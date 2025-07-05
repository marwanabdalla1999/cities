package com.klivver.cities

import android.content.Intent
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.klivver.cities.composable.CitySearchContent
import com.klivver.ui.composeUi.EmptyState
import com.klivver.ui.composeUi.LoadingState
import com.klivver.ui.composeUi.SearchBar
import com.klivver.cities.uiContract.CitiesSideEffect
import com.klivver.cities.uiContract.CitiesViewEvent
import com.klivver.cities.uiContract.CitiesViewState
import kotlinx.coroutines.flow.Flow
import androidx.core.net.toUri

@Composable
fun CitiesScreen(
    state: CitiesViewState,
    setEvent: (CitiesViewEvent) -> Unit,
    effects: Flow<CitiesSideEffect>,
    modifier: Modifier = Modifier
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        effects.collect {
            when (it) {
                is CitiesSideEffect.OpenMap -> {
                    val uri = "geo:${it.lat},${it.lon}".toUri()
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)

                }

                is CitiesSideEffect.ShowError -> {
                    snackBarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    Scaffold(modifier = modifier, content = { paddingValues ->
        if (state.isLoading) LoadingState()
        else if (state.cities.isEmpty()) EmptyState()
        else {
            CitySearchContent(
                cities = state.cities,
                setEvent = setEvent,
                modifier = Modifier.padding(paddingValues),
            )
        }
    }, bottomBar = {
        SearchBar(
            query = state.query,
            onSearch = { prefix -> setEvent(CitiesViewEvent.Search(prefix)) },
            modifier = Modifier.imePadding()
        )
    }, snackbarHost = { SnackbarHost(snackBarHostState) }
    )
}
package com.klivver.cities

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.klivver.ui.navigations.Cities

fun NavGraphBuilder.citiesRoute(
    modifier: Modifier = Modifier
) {

    composable<Cities> {
        val viewModel: CitiesViewModel = hiltViewModel()
        val state by viewModel.viewState.collectAsStateWithLifecycle()
        val effects = viewModel.effect
        CitiesScreen(
            state = state,
            setEvent = viewModel::setEvent,
            effects = effects,
            modifier = modifier.background(Color(0xFFF5F4F6)),
        )
    }


}
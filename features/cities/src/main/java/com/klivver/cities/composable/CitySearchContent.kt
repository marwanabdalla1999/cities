package com.klivver.cities.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klivver.cities.uiContract.CitiesViewEvent
import com.klivver.cities.models.City

@Composable
fun CitySearchContent(
    cities: List<City>,
    setEvent: (CitiesViewEvent) -> Unit,
    modifier: Modifier
) {
    AnimatedVisibility(true) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "City Search",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "${cities.size} cities",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 14.sp,
            color = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CityList(
            cities = cities,
            onSelect = { city -> setEvent(CitiesViewEvent.CitySelected(city)) }
        )

    }
    }
} 
package com.klivver.cities.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.klivver.cities.models.City

@Composable
fun CityList(
    cities: List<City>, onSelect: (City) -> Unit
) {
    val grouped = cities.groupBy { it.name.first().uppercaseChar() }
    Box {
        Box(
            modifier = Modifier
                .padding(start = 40.dp)
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.LightGray)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            grouped.toSortedMap().forEach { (letter, group) ->

                stickyHeader {
                    CityGroupHeader(letter = letter)
                }
                itemsIndexed(group.sortedWith(compareBy({ it.name }, { it.country }))) { _, city ->
                    CityListItem(
                        city = city,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(city) }
                            .padding(vertical = 4.dp)
                            .padding(start = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp)),)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}
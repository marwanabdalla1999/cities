package com.klivver.cities.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CityGroupHeader(letter: Char,modifier: Modifier = Modifier) {

        Box(
            modifier = modifier
                .size(50.dp)
                .border(width = 1.dp, color = Color.LightGray, CircleShape)
                .background(Color.White,CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = letter.toString(), fontSize = 20.sp, color = Color.Black)
        }


}
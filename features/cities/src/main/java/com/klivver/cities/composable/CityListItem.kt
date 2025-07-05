package com.klivver.cities.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klivver.cities.models.City
import java.util.Locale

@Composable
fun CityListItem(
    city: City,
    modifier: Modifier=Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFFECECEF), shape = CircleShape),
                    contentAlignment = Alignment.Center){
                    Text(text = getFlagEmoji(city.country), fontSize = 32.dp.value.sp)

                }

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${city.name}, ${city.country}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    fontSize = 14.sp
                )
                Text(
                    text = "${city.lat}, ${city.lon}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF4D4D4D),
                    fontSize = 11.sp
                )
            }
        }
    }
}

// Simple country code to flag emoji (for demo)
fun getFlagEmoji(countryCode: String): String {
    val safeCode = countryCode.uppercase(Locale.ROOT)
    if (safeCode.length != 2 || safeCode.any { it !in 'A'..'Z' }) return "🏳️"
    return safeCode.map { char ->
        0x1F1E6 - 'A'.code + char.code
    }.joinToString("") { codePoint ->
        String(Character.toChars(codePoint))
    }
}
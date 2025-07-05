package com.klivver.ui.composeUi

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.klivver.ui.R

@Composable
fun SearchBar(
    query: String,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var focused by remember { mutableStateOf(false) }

    val bg by animateColorAsState(
        targetValue = if (focused) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.White,
        animationSpec = tween(300)
    )
    val elev by animateDpAsState(
        targetValue = if (focused) 8.dp else 2.dp,
        animationSpec = tween(300)
    )

    Surface(
        modifier = modifier.clip(RoundedCornerShape(10.dp)),
        tonalElevation = elev
    ) {
        TextField(
            value = query,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            onValueChange = {
                onSearch(it)
            },
            placeholder = { Text(stringResource(R.string.search)) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focused = it.isFocused }
                .background(bg)
                .padding(horizontal = 16.dp, vertical = 30.dp)
        )

    }
} 
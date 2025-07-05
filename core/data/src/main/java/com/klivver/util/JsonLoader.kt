package com.klivver.util

import android.content.Context

object JsonLoader {
    fun loadJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
} 
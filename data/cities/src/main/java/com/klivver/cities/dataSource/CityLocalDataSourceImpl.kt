package com.klivver.cities.dataSource

import android.content.Context
import com.klivver.Constants.CITIES_JSON_FILE
import com.klivver.Constants.YOU_MUST_CALL_PRELOADED_FIRST
import com.klivver.cities.models.CityDto
import com.klivver.util.JsonLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A local data source that loads and provides in-memory access to city data
 * from a static JSON file stored in the app's assets.
 *
 * It supports efficient prefix-based search using binary search on a pre-sorted list.
 *
 * This class ensures:
 * - The city list is only loaded once (idempotent `preloadCities()`).
 * - Sorting is done in a background dispatcher.
 * - Search is performed efficiently and off the main thread.
 *
 * @property context Android application context for accessing assets
 */

class CityLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ICityLocalDataSource {

    private lateinit var cityList: List<CityDto>
    private var isLoaded = false

    override suspend fun preloadCities() {
        if (isLoaded) return

        val jsonString = withContext(Dispatchers.IO) {
            JsonLoader.loadJsonFromAssets(context, CITIES_JSON_FILE)
        }

        val parsedSorted = withContext(Dispatchers.Default) {
            val dtos: List<CityDto> = Json.decodeFromString(jsonString)
            dtos.sortedWith(
                compareBy(
                    { it.name.lowercase(Locale.ROOT) },
                    { it.country.lowercase(Locale.ROOT) }
                )
            )
        }

        cityList = parsedSorted
        isLoaded = true
    }

    /**
     * Returns the full list of cities loaded in memory.
     *
     * @throws IllegalStateException if [preloadCities] has not been called yet
     */
    override fun getAllCities(): List<CityDto> {
        check(isLoaded) { YOU_MUST_CALL_PRELOADED_FIRST }
        return cityList
    }

    /**
     * Searches for cities whose names start with the given [prefix].
     *
     * This is a case-insensitive prefix match. It uses binary search to find the
     * first matching city, then iterates forward to collect all results.
     *
     * @param prefix the string to search for at the beginning of city names
     * @return a list of [CityDto] matching the prefix
     * @throws IllegalStateException if [preloadCities] has not been called yet
     */
    override suspend fun searchCitiesByPrefix(prefix: String): List<CityDto> {
        check(isLoaded) { YOU_MUST_CALL_PRELOADED_FIRST }

        return withContext(Dispatchers.Default) {
            if (prefix.isEmpty()) return@withContext cityList

            val p = prefix.lowercase(Locale.ROOT)
            // 1) Find lower‐bound insertion index for p
            val idx = cityList.binarySearch { city ->
                city.name.lowercase(Locale.ROOT).compareTo(p).let { cmp ->
                    // return negative if city.name < p, zero if city.name startsWith p, positive otherwise
                    if (city.name.lowercase(Locale.ROOT).startsWith(p)) 0 else cmp
                }
            }.let { if (it < 0) -it - 1 else it }

            // 2) Collect from idx forward as long as startsWith(p)
            val result = mutableListOf<CityDto>()
            for (i in idx until cityList.size) {
                val name = cityList[i].name.lowercase(Locale.ROOT)
                if (!name.startsWith(p)) break
                result += cityList[i]
            }
            result
        }

    }
}
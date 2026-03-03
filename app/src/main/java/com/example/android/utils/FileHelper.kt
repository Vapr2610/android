package com.example.android.utils

import android.content.Context
import com.google.gson.Gson
import com.example.android.data.LocationData
import java.io.File

object FileHelper {

    private const val LOCATION_FILE = "locations.json"
    private val gson = Gson()

    fun saveLocation(context: Context, data: LocationData) {
        try {
            val json = gson.toJson(data)
            val file = File(context.filesDir, LOCATION_FILE)
            file.appendText(json + "\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadLocations(context: Context): List<LocationData> {
        return try {
            val file = File(context.filesDir, LOCATION_FILE)
            if (!file.exists()) return emptyList()

            val lines = file.readLines()
            lines.mapNotNull { line ->
                try {
                    gson.fromJson(line, LocationData::class.java)
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun clearLocations(context: Context): Boolean {
        return try {
            File(context.filesDir, LOCATION_FILE).delete()
            true
        } catch (e: Exception) {
            false
        }
    }
}
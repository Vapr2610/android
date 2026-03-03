package com.example.android.data

import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("altitude") val altitude: Double,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("accuracy") val accuracy: Float? = null
) {
    fun isValid(): Boolean {
        return latitude in -90.0..90.0 &&
                longitude in -180.0..180.0 &&
                timestamp > 0
    }
}
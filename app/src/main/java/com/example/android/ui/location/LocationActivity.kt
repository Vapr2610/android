package com.example.android.ui.location

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.android.R
import com.example.android.data.LocationData
import com.example.android.utils.FileHelper
import com.example.android.utils.PermissionHelper
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.text.SimpleDateFormat
import java.util.*

class LocationActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var tvAltitude: TextView
    private lateinit var tvTime: TextView
    private lateinit var btnRefresh: Button

    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        initViews()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (PermissionHelper.hasLocationPermissions(this)) {
            getCurrentLocation()
        } else {
            PermissionHelper.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST)
        }
    }

    private fun initViews() {
        tvLatitude = findViewById(R.id.tvLatitude)
        tvLongitude = findViewById(R.id.tvLongitude)
        tvAltitude = findViewById(R.id.tvAltitude)
        tvTime = findViewById(R.id.tvTime)
        btnRefresh = findViewById(R.id.btnRefresh)

        btnRefresh.setOnClickListener {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location: Location? ->
            location?.let {
                val data = LocationData(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    altitude = it.altitude,
                    timestamp = System.currentTimeMillis(),
                    accuracy = it.accuracy
                )
                displayLocation(data)
                FileHelper.saveLocation(this, data)
            } ?: run {
                tvLatitude.text = getString(R.string.location_waiting)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Ошибка: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayLocation(data: LocationData) {
        val timeFormat = SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.getDefault())
        val timeString = timeFormat.format(Date(data.timestamp))

        tvLatitude.text = "${getString(R.string.location_latitude)} ${String.format("%.6f", data.latitude)}°"
        tvLongitude.text = "${getString(R.string.location_longitude)} ${String.format("%.6f", data.longitude)}°"
        tvAltitude.text = "${getString(R.string.location_altitude)} ${String.format("%.2f", data.altitude)} м"
        tvTime.text = "${getString(R.string.location_time)} $timeString"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                getCurrentLocation()
            } else {
                tvLatitude.text = getString(R.string.location_permission_denied)
                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
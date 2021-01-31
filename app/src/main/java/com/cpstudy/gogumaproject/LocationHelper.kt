package com.cpstudy.gogumaproject

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task

class LocationHelper(private val activity: Activity) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var callback: (LatLng) -> Unit

    fun location(callback: (LatLng) -> (Unit)) {
        this.callback = callback
        LocationServices.getFusedLocationProviderClient(activity)

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = locationRequest.let {
            LocationSettingsRequest.Builder().addLocationRequest(it)
        }

        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder?.build())
        task.addOnSuccessListener {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            checkPermission()
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }

    }

    private val locationCallback: LocationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val latitude = locationResult.lastLocation.latitude
            val longitude = locationResult.lastLocation.longitude
            fusedLocationClient.removeLocationUpdates(this)
            callback(LatLng(latitude, longitude))
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity.applicationContext, "권한이 없습니다. 안타깝습니다.", Toast.LENGTH_LONG).show()
            return
        }
    }
}
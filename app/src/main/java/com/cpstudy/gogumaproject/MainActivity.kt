package com.cpstudy.gogumaproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.cpstudy.gogumaproject.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: MainViewModel by viewModels()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mMap: GoogleMap

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if(isGranted) {
            // Permission is granted. Contimue the action of workflow in your app.
            Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
            Log.e("TAG", "다시 묻지 않음")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        checkPermission()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnSuggest.setOnClickListener {
            requestDeviceLocation(this) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        requestDeviceLocation(this) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        }
    }

    private fun checkPermission() {

        when {
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED -> {

            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for the a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                Log.e("TAG", "권한을 해야합니다.")
                Toast.makeText(applicationContext, "권한을 설정해야합니다.", Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}
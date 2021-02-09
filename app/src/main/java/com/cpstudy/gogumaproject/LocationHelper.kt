package com.cpstudy.gogumaproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.cpstudy.gogumaproject.retro.Restaurant
import com.cpstudy.gogumaproject.retro.RestaurantAPI
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * 현재 위치의 위도와 경도 값을 구해 [onSuccess] 수행
 *
 * @param onSuccess 위치 권한 허용 후 실행할 함수
 * [LatLng] 현재 위도, 경도
 * [Restaurant] 주변 식당 정보
 */
fun requestDeviceLocation(context: Context, onSuccess: (LatLng, Restaurant) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = LocationRequest.create()
            .setInterval(10000)
            .setFastestInterval(5000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

    val locationCallback: LocationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val latitude = locationResult.lastLocation.latitude
            val longitude = locationResult.lastLocation.longitude
            fusedLocationClient.removeLocationUpdates(this)
            getRestaurants(LatLng(latitude, longitude), onSuccess)
        }
    }

    LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
            .let {
                LocationServices.getSettingsClient(context).checkLocationSettings(it)
            }.addOnSuccessListener {
                if(checkPermission(context)) {
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
                } else {
                    Toast.makeText(context, "권한 설정을 하세요.", Toast.LENGTH_LONG).show()
                }
            }
}

private fun getRestaurants(latLng: LatLng, onSuccess: (LatLng, Restaurant) -> Unit) {
    val retro = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retro.create(RestaurantAPI::class.java)
    val callGetRestaurant = api.getNearRestaurants(
        "AIzaSyAZlSgYZO3kimuB7VQC0xddYJWUrQfs1gE",
        "restaurant",
        "${latLng.latitude}, ${latLng.longitude}",
        1000
    )

    callGetRestaurant
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d("rx", it.toString())
                onSuccess(latLng, it)
            }
            .doOnError {
                Log.e("rx", it.toString())
            }
            .subscribe()


//    testFuntionObservable1()
//        .subscribeOn(Schedulers.io())
//        .flatMap {
//            testFuntionObservable2()
//        }.flatMap {
//            testFuntionObservable2()
//        }.flatMapSingle {
//            callGetRestaurant
//        }
//        .observeOn(AndroidSchedulers.mainThread())
//        .doOnNext {
//            Log.d("rxkotlin", "성공: $it")
//            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
//        }
//        .doOnError {
//            Log.e("rxkotlin", it.toString())
//        }
//        .subscribe()

//    GlobalScope.launch {
//        val result1 = testFuntionObservable1().blockingFirst()
//        val result2 = testFuntionObservable1().blockingFirst()
//        val restaurant = api.getNearRestaurants(
//            "AIzaSyAZlSgYZO3kimuB7VQC0xddYJWUrQfs1gE",
//            "restaurant",
//            "37.4743187, 127.1509092",
//            1000
//        )
//    }
}
private fun testFuntionObservable1(): Observable<Int> {
    return Observable.fromCallable {
        Thread.sleep(1000)
        10
    }
}

private fun testFuntionObservable2(): Observable<Int> {
    return Observable.fromCallable {
        Thread.sleep(1000)
        10
    }
}

private fun testFunction1(callback: (Int) -> Unit) {
    Thread.sleep(1000)
    callback(10)
}

private fun testFunction2(callback: (Int) -> Unit) {
    Thread.sleep(1000)
    callback(10)
}

private fun checkPermission(context: Context): Boolean {
    return !(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
}

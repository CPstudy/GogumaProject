package com.cpstudy.gogumaproject.retro

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantAPI {
    @GET("/maps/api/place/textsearch/json")
    fun getNearRestaurants(
        @Query("key") key: String,
        @Query("query") query: String,
        @Query("location") location: String,
        @Query("radius") radius: Int
    ) : Observable<Restaurant>

//    @GET("/maps/api/place/textsearch/json")
//    fun getNearRestaurants(
//        @Query("key") key: String,
//        @Query("query") query: String,
//        @Query("location") location: String,
//        @Query("radius") radius: Int
//    ) : Single<Restaurant>

//    @GET("/maps/api/place/textsearch/json")
//    suspend fun getNearRestaurants(
//        @Query("key") key: String,
//        @Query("query") query: String,
//        @Query("location") location: String,
//        @Query("radius") radius: Int
//    ) : Restaurant
}
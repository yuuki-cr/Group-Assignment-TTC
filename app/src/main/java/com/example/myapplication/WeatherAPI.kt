package com.example.myapplication


import com.example.myapplication.api_items.WeatherJSON
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/data/2.5/weather?appid=cb170c74e16ff3b825e05ba8683e42f5&units=metric")
    fun getData(@Query("q") country: String): Call<WeatherJSON>
}
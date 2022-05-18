package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.api_items.WeatherJSON
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWeatherData()
    }

    private fun getWeatherData() {
        val apiRequest = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
            .getData("singapore")

        apiRequest.enqueue(object : Callback<WeatherJSON?> {
            override fun onResponse(call: Call<WeatherJSON?>, response: Response<WeatherJSON?>) {
                val body: WeatherJSON? = response.body()

                val country = findViewById<TextView>(R.id.country)
                val weather = findViewById<TextView>(R.id.weather)

                if (body != null) {
                    country.text = body.name
                    weather.text = body.weather[0].description
                }
                else {
                    country.text = "Country not found!"
                }
            }

            override fun onFailure(call: Call<WeatherJSON?>, t: Throwable) {
                val country = findViewById<TextView>(R.id.country)

                country.text = t.message
            }
        })
    }
}
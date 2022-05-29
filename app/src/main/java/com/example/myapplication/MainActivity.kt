package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.getCurrentData_items.WeatherJSON
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        getCurrentWeather("Singapore")

        val searchBtn = findViewById<Button>(R.id.searchBtn)
        val searchInput = findViewById<EditText>(R.id.searchInput)

        searchBtn.setOnClickListener {
            getCurrentWeather(searchInput.text.toString())
            hideKeyboard()
        }
    }

    private fun getCurrentWeather(location: String) {
        val apiRequest = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
            .getData(location)

        apiRequest.enqueue(object : Callback<WeatherJSON?> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<WeatherJSON?>, response: Response<WeatherJSON?>) {
                val body: WeatherJSON? = response.body()

                val country = findViewById<TextView>(R.id.country)
                val temperature = findViewById<TextView>(R.id.temperature)

                if (body != null) {
                    val temperatureRange = findViewById<TextView>(R.id.temperatureRange)
                    val feelsLike = findViewById<TextView>(R.id.feelsLikeTemperature)

                    country.text = body.name
                    temperature.text = "%.0f".format(body.main.temp) + "°"
                    temperatureRange.text = "%.0f".format(body.main.temp_max) + "° / " + "%.0f".format(body.main.temp_min) + "°"
                    feelsLike.text =  "Feels like " + "%.0f".format(body.main.feels_like) + "°"
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


    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
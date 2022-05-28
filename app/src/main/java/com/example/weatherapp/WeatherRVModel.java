package com.example.weatherapp;

public class WeatherRVModel {

    private String temperature;

    public WeatherRVModel(String time, String temperature, String windSpeed) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}

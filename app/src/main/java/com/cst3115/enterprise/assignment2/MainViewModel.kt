package com.cst3115.enterprise.assignment2

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    private val _forecastState = MutableStateFlow<ForecastResponse?>(null)
    val forecastState: StateFlow<ForecastResponse?> = _forecastState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun fetchWeatherData(cityName: String) {
        viewModelScope.launch {
            try {
                val apiKey = "1bed5f996642ca31fa2d09c7b49881c4" // Replace with your actual API key
                val weatherResponse = ApiClient.apiService.getCurrentWeather(cityName, apiKey)
                val forecastResponse = ApiClient.apiService.getFiveDayForecast(cityName, apiKey)
                _weatherState.value = weatherResponse
                _forecastState.value = forecastResponse
                _errorState.value = null
            } catch (e: IOException) {
                _errorState.value = "No internet connection."
            } catch (e: HttpException) {
                _errorState.value = "Error fetching data. Please try again later."
            } catch (e: Exception) {
                _errorState.value = "An unexpected error occurred."
            }
        }
    }

}


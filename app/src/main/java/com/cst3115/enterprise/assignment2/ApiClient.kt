package com.cst3115.enterprise.assignment2


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/") // Base URL of the API
        .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON parsing
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

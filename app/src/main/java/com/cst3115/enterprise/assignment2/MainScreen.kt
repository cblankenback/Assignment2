package com.cst3115.enterprise.assignment2



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToSettings: () -> Unit = {},
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val weatherState by viewModel.weatherState.collectAsState()
    val forecastState by viewModel.forecastState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    LaunchedEffect(Unit) {
        val savedCityName = preferencesManager.getCityName()
        if (!savedCityName.isNullOrEmpty()) {
            viewModel.fetchWeatherData(savedCityName)
        } else {
            onNavigateToSettings()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather App") },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary),

                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when {
                    weatherState != null && forecastState != null -> {
                        val dailyForecasts = getDailyForecasts(forecastState!!)
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Current Weather in ${weatherState!!.name}: ${weatherState!!.main.temp}°C",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = "https://openweathermap.org/img/wn/${weatherState!!.weather[0].icon}@2x.png",
                                contentDescription = null,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "5-Day Forecast:",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn {
                                items(dailyForecasts) { item ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "${item.date}: ${item.temperature}°C, ${item.description}"
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        AsyncImage(
                                            model = "https://openweathermap.org/img/wn/${item.icon}@2x.png",
                                            contentDescription = null,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    errorState != null -> {
                        Text(
                            text = "Error: ${errorState}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    )
}

data class DailyForecast(
    val date: String,
    val temperature: Double,
    val description: String,
    val icon: String
)

fun getDailyForecasts(forecastResponse: ForecastResponse): List<DailyForecast> {
    val groupedData = forecastResponse.list.groupBy { forecastItem ->
        // Extract the date without time
        forecastItem.dt_txt.substring(0, 10)
    }

    val dailyForecasts = groupedData.map { (date, forecasts) ->
        // Find the forecast item closest to 12:00 PM
        val targetTime = "12:00:00"
        val forecastAtNoon = forecasts.find { it.dt_txt.contains(targetTime) }
            ?: forecasts[forecasts.size / 2] // If no exact match, pick the middle item

        DailyForecast(
            date = date,
            temperature = forecastAtNoon.main.temp,
            description = forecastAtNoon.weather[0].description,
            icon = forecastAtNoon.weather[0].icon
        )
    }

    // Sort the list by date
    return dailyForecasts.sortedBy { it.date }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}

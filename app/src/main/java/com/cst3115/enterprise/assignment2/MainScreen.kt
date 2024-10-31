package com.cst3115.enterprise.assignment2


import android.util.Log
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
import coil.ImageLoader
import coil.util.DebugLogger


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
                        Column(modifier = Modifier.padding(16.dp)) {
                            val imageUrl = "https://openweathermap.org/img/wn/${weatherState!!.weather[0].icon}@2x.png"
                            val imageLoader = ImageLoader.Builder(context)
                                .logger(DebugLogger())
                                .build()
                            Text(text = "Image URL: $imageUrl")
                            Text(
                                text = "Current Weather in ${weatherState!!.name}: ${weatherState!!.main.temp}°C",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = "https://openweathermap.org/img/wn/${weatherState!!.weather[0].icon}@2x.png",
                                //model = "https://openweathermap.org/img/wn/04d@2x.png",
                               // model = "https://www.color-hex.com/palettes/3595.png"
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                imageLoader = imageLoader,
                                onError = { error ->
                                    //Text(text = "Image failed to load.")
                                    Log.e("AsyncImage", "Error loading image: ${error.result.throwable}")
                                }

                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "5-Day Forecast:",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn {
                                items(forecastState!!.list) { item ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        Text(text = "${item.dt_txt}: ${item.main.temp}°C, ${item.weather[0].description}")
                                        Spacer(modifier = Modifier.width(8.dp))
                                        AsyncImage(
                                            model = "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png",
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
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}

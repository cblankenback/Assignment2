package com.cst3115.enterprise.assignment2

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons


import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigateBack: () -> Unit = {}) {
    var cityName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    // Initialize cityName with the saved value
    LaunchedEffect(Unit) {
        cityName = preferencesManager.getCityName() ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary)

                )
        }
    ) { contentPadding ->
        // Use the contentPadding parameter provided by Scaffold
        Column(
            modifier = Modifier
                .padding(contentPadding) // Apply the contentPadding to the Column
                .padding(16.dp) // Additional padding for layout
        ) {
            Text(text = "Current City: ${if (cityName.isNotEmpty()) cityName else "None"}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Enter City Name")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = cityName,
                onValueChange = { cityName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("City Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    preferencesManager.saveCityName(cityName)
                    Toast.makeText(context, "City saved!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen()
    }
}
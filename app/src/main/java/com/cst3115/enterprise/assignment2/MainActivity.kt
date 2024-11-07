package com.cst3115.enterprise.assignment2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cst3115.enterprise.assignment2.ui.theme.Assignment2Theme

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scheduleWeatherUpdates(applicationContext)
        setContent {
            Assignment2Theme {
                AppContent()
            }
        }
    }

    private fun scheduleWeatherUpdates(context: Context) {
        val weatherWorkRequest = PeriodicWorkRequestBuilder<WeatherWorker>(3, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueue(weatherWorkRequest)
    }
}

class WeatherWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {

        fetchWeatherUpdates()
        return Result.success()
    }

    private fun fetchWeatherUpdates() {

        println("Fetching weather updates...")
    }
}
@Composable
fun AppContent() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(
                onNavigateToSettings = {
                    navController.navigate("settings_screen")
                }
            )
        }
        composable("settings_screen") {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("test_screen") {
            TestScreen()
        }
    }
}

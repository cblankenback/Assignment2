// In your ViewModel or a test function
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import com.cst3115.enterprise.assignment2.ApiClient

class TestViewModel : ViewModel() {

    private val TAG = "TestViewModel"

    fun testApiCall() {
        viewModelScope.launch {
            try {
                val apiKey = "1bed5f996642ca31fa2d09c7b49881c4" // Replace with your actual API key
                val cityName = "Toronto" // You can change this to any valid city name

                // Make the API call to fetch current weather
                val weatherResponse = ApiClient.apiService.getCurrentWeather(cityName, apiKey)

                // Log the fetched data
                Log.d(TAG, "City: ${weatherResponse.name}")
                Log.d(TAG, "Temperature: ${weatherResponse.main.temp}°C")
                Log.d(TAG, "Weather: ${weatherResponse.weather[0].description}")
                Log.d(TAG, "Icon: ${weatherResponse.weather[0].icon}")
            } catch (e: Exception) {
                // Log any errors
                Log.e(TAG, "API call failed: ${e.message}")
            }
        }
    }
}
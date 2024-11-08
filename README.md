# OpenWeatherMap API Setup Instructions

This application uses the OpenWeatherMap API to fetch current weather and forecast data.

## Step 1: Obtain an API Key
- Visit the [OpenWeatherMap website](https://openweathermap.org/) and sign up for a free account.
- After logging in, navigate to the **API Keys** section of your account dashboard.
- Generate a new API key, which will be required to access the API.

## Step 2: Add the API Key to the Project
- In your project, add the API key directly within the `MainViewModel` class.
  - Open the `MainViewModel` class located at `com.cst3115.enterprise.assignment2.MainViewModel`.
  - Locate the line where the API key is defined:
    ```kotlin
    val apiKey = "1bed5f996642ca31fa2d09c7b49881c4" // Replace with your actual API key
    ```
  - Replace the placeholder string with your actual API key:
    ```kotlin
    val apiKey = "your_actual_api_key_here" // Replace with your actual API key
    ```



## Example Usage
- To get the current weather or a five-day forecast, use the following parameters:
  - `cityName`: The name of the city (e.g., "London").
  - `appid`: Your API key defined in the `MainViewModel`.


---

# Key Features

- **Current Weather Display**: Shows real-time weather data, including temperature, humidity, and general conditions for the selected city.
- **5-Day Weather Forecast**: Provides a forecast view displaying predicted weather conditions for the next five days.
- **City Selection**: Users can select their preferred city for weather information through the Settings screen.
- **Persistent Data Storage**: Stores the selected city in `SharedPreferences` to retain the city choice even after the app is closed.
- **Weather Icons**: Displays context-specific icons (e.g., sun, rain) based on the current weather conditions.
- **Periodic Weather Updates**: Uses `WorkManager` to fetch weather data every 3 hours, ensuring up-to-date information.
- **Error Handling**: Implements basic error handling, such as displaying an error message if the data fetch fails or if there’s no internet connection.

---

# WorkManager

The `WorkManager` component handles periodic background tasks by scheduling a worker to fetch updated weather data for the selected city every three hours. This ensures that the app displays the latest weather conditions without requiring manual refreshes.

### Responsibilities of the Worker Class:
- **Network Requests**: Makes network requests to the weather API.
- **UI Updates**: Updates the UI with the latest fetched data, providing fresh weather updates even if the app hasn't been opened recently.

---

# Assumptions or Limitations

- **Internet Requirement**: An active internet connection is required to retrieve weather data. Offline functionality is limited.
- **Data Accuracy**: The app relies on the OpenWeatherMap API; the accuracy of the weather data depends on the API's data quality.
- **City Selection**: Allows users to select only one city at a time. Users must update their selection in the Settings screen to view weather for a different city.


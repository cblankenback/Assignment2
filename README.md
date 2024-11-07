Setup Instructions

This application uses the OpenWeatherMap API to fetch current weather and forecast data.

Step 1: Obtain an API Key
	- Go to the OpenWeatherMap website and sign up for a free account.
	- After logging in, navigate to the API Keys section of your account dashboard.
	- Generate a new API key, which will be required to access the API.

Step 2: Add the API Key to the Project
	- In the application code, the API key is passed as a parameter to the API service. 
	- You can add your API key in a secure way by:
	- Creating a new file in your project to store the API key securely (e.g., local.properties or gradle.properties).
		- Define a key-value pair in this file, like this: OPENWEATHER_API_KEY=your_api_key_here
		- Access this key in your code by referencing it as BuildConfig.OPENWEATHER_API_KEY.

Example Usage
	- To get the current weather or a five-day forecast, use the following parameters:
		- cityName: The name of the city (e.g., "London").
		- appid: Your API key.
		- units: (Optional) Units of measurement. Use "metric" for Celsius, "imperial" for Fahrenheit.

——————————————————————————————————————————————————————————————

Key Features

Current Weather Display: Shows real-time weather data, including temperature, humidity, and general conditions for the selected city.

5-Day Weather Forecast: Provides a forecast view displaying predicted weather conditions for the next five days.

City Selection: Users can select their preferred city for weather information through the Settings screen.

Persistent Data Storage: Stores the selected city in SharedPreferences so the app retains the city choice even after closing.

Weather Icons: Displays context-specific icons (e.g., sun, rain) based on the current weather conditions.

Periodic Weather Updates: The app uses WorkManager to fetch weather data every 3 hours, ensuring up-to-date information.

Error Handling: Implements basic error handling, such as displaying an error message if the data fetch fails or if there’s no internet connection.

——————————————————————————————————————————————————————————————

WorkManager

The WorkManager component is set up to handle periodic background tasks by scheduling a worker to fetch updated weather data for the selected city every three hours. This ensures that the app displays the latest weather conditions without requiring the user to manually refresh. 

The Worker class is responsible for:
	- Making network requests to the weather API.
	- Updating the UI with the latest fetched data, providing users with fresh weather updates even if they haven’t actively opened the app recently.

——————————————————————————————————————————————————————————————

Assumptions or Limitations

Internet Requirement: The app requires an active internet connection to retrieve weather data. Offline functionality is limited, as the app does not store past weather data.

Data Accuracy: The app relies on the OpenWeatherMap API, and the accuracy of the weather data depends on the quality of data provided by the API.

City Selection: The app allows users to select only one city at a time. To view weather data for a different city, users must update their selection in the Settings screen.






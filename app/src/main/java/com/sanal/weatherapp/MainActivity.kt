package com.sanal.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanal.weatherapp.network.RetrofitClient
import com.sanal.weatherapp.ui.theme.WeatherAppTheme
import com.sanal.weatherapp.data.WeatherResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppScreen(viewModel = WeatherViewModel())
        }
    }
}

class WeatherViewModel : ViewModel() {
    private val _weatherData = mutableStateOf<WeatherResponse?>(null)
    val weatherData: State<WeatherResponse?> = _weatherData

    private val _city = mutableStateOf("")
    val city: State<String> = _city

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun updateCity(newCity: String) {
        _city.value = newCity
    }

    fun fetchWeatherData(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitClient.apiService.getCurrentWeather(
                    apiKey = "", /* https://www.weatherapi.com/ */
                    city = city
                )
                _weatherData.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Hava durumu bilgileri alınırken bir hata oluştu."
                _weatherData.value = null
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

@Composable
fun WeatherAppScreen(viewModel: WeatherViewModel) {
    val weatherState = viewModel.weatherData
    val cityState = viewModel.city
    val isLoadingState = viewModel.isLoading
    val errorState = viewModel.errorMessage


    WeatherAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = cityState.value,
                    onValueChange = viewModel::updateCity,
                    label = { Text("Şehir Adı") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = { viewModel.fetchWeatherData(cityState.value) }) {
                    Text("Ara")
                }

                if (isLoadingState.value) {
                    CircularProgressIndicator()
                } else if (errorState.value != null) {
                    Text(errorState.value!!)
                } else if (weatherState.value != null) {
                    WeatherInfoCard(weatherResponse = weatherState.value!!)
                } else {
                    Text("Hava durumu bilgisi girmek için bir şehir arayın.")
                }
            }
        }
    }
}

@Composable
fun WeatherInfoCard(weatherResponse: WeatherResponse) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "${weatherResponse.location.name}, ${weatherResponse.location.country}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = weatherResponse.current.condition.icon)
        Text(text = weatherResponse.current.condition.text, style = MaterialTheme.typography.bodyLarge)
        Text(text = "${weatherResponse.current.tempC}°C", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Rüzgar: ${weatherResponse.current.windKph} km/s")
        Text(text = "Nem: %${weatherResponse.current.humidity}")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppScreen(viewModel = WeatherViewModel())
}
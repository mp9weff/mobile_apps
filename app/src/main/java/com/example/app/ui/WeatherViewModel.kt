package com.example.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.ForecastItem
import com.example.app.data.WeatherRepository
import com.example.app.data.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WeatherUiState(
    val currentWeather: WeatherResponse? = null,
    val forecast: List<ForecastItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val cityName: String = ""
)

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun loadWeather(city: String) {
        if (city.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, cityName = city)

            val currentResult = repository.getCurrentWeather(city)
            val forecastResult = repository.getForecast(city)

            _uiState.value = _uiState.value.copy(isLoading = false)

            currentResult.getOrNull()?.let { weather ->
                _uiState.value = _uiState.value.copy(
                    currentWeather = weather,
                    error = null
                )
            } ?: run {
                _uiState.value = _uiState.value.copy(
                    error = "Помилка завантаження погоди: ${currentResult.exceptionOrNull()?.message ?: "Невідома помилка"}",
                    currentWeather = null
                )
            }

            forecastResult.getOrNull()?.let { forecast ->
                val dailyForecast = forecast.list
                    .groupBy { it.dtTxt.substring(0, 10) }
                    .values
                    .map { it.first() }
                    .take(5)

                _uiState.value = _uiState.value.copy(
                    forecast = dailyForecast,
                    error = null
                )
            } ?: run {
                if (_uiState.value.error == null) {
                    _uiState.value = _uiState.value.copy(
                        error = "Помилка завантаження прогнозу: ${forecastResult.exceptionOrNull()?.message ?: "Невідома помилка"}"
                    )
                }
            }
        }
    }
}


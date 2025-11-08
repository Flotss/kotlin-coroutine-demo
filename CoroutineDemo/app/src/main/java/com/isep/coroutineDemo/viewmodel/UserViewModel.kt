package com.isep.coroutineDemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isep.coroutineDemo.data.User
import com.isep.coroutineDemo.network.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val users: List<User>) : UiState()
    data class Error(val message: String) : UiState()
}

class UserViewModel : ViewModel() {
    private val apiService = ApiService.create()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _timeUntilRefresh = MutableStateFlow(30)
    val timeUntilRefresh: StateFlow<Int> = _timeUntilRefresh.asStateFlow()

    private val _isAutoRefreshEnabled = MutableStateFlow(true)
    val isAutoRefreshEnabled: StateFlow<Boolean> = _isAutoRefreshEnabled.asStateFlow()

    init {
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                if (_isAutoRefreshEnabled.value) {
                    fetchUsers()

                    // Compteur de 30 secondes
                    for (i in 30 downTo 1) {
                        _timeUntilRefresh.value = i
                        delay(1000)

                        // Si l'auto-refresh est désactivé, arrêter le compteur
                        if (!_isAutoRefreshEnabled.value) break
                    }
                } else {
                    delay(1000)
                }
            }
        }
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = apiService.getRandomUsers(5)
                _uiState.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    e.message ?: "Erreur inconnue lors du chargement des utilisateurs"
                )
            }
        }
    }

    fun toggleAutoRefresh() {
        _isAutoRefreshEnabled.value = !_isAutoRefreshEnabled.value
    }
}


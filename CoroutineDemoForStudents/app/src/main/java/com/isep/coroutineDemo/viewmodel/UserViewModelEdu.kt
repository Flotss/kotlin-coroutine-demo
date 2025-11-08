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

/**
 * ==========================================
 * 🎓 EXERCICE PRATIQUE - COROUTINES
 * ==========================================
 *
 * Objectifs d'apprentissage :
 * 1. Comprendre les StateFlow et leur utilisation
 * 2. Utiliser viewModelScope pour lancer des coroutines
 * 3. Implémenter un système d'auto-refresh avec delay()
 * 4. Gérer les états de l'UI (Loading, Success, Error)
 * 5. Effectuer des appels API asynchrones avec try-catch
 */

sealed class UiState {
    object Loading : UiState()
    data class Success(val users: List<User>) : UiState()
    data class Error(val message: String) : UiState()
}

class UserViewModelEdu : ViewModel() {
    private val apiService = ApiService.create()

    // État de l'UI (Loading, Success, Error)
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // TODO 1: Créez un StateFlow pour le temps restant avant le prochain refresh (initialiser à 30)

    // TODO 2: Créez un StateFlow pour activer/désactiver l'auto-refresh (initialiser à true)

    init {
        // TODO 3: Appelez startAutoRefresh() dans le bloc init
    }

    /**
     * TODO 4: Implémentez la fonction startAutoRefresh()
     *
     * Cette fonction doit :
     * - Lancer une coroutine avec viewModelScope.launch { }
     * - Créer une boucle infinie avec while(true)
     * - Vérifier si l'auto-refresh est activé
     * - Si activé :
     *   - Appeler fetchUsers()
     *   - Créer un compteur de 30 à 1 avec une boucle for
     *   - Mettre à jour _timeUntilRefresh.value à chaque itération
     *   - Utiliser delay(1000) pour attendre 1 seconde
     *   - Vérifier si l'auto-refresh est toujours activé dans la boucle
     * - Si désactivé :
     *   - Attendre 1 seconde avec delay(1000)
     *
     * Hints:
     * - viewModelScope.launch { } pour lancer une coroutine
     * - while(true) pour une boucle infinie
     * - for (i in 30 downTo 1) pour compter de 30 à 1
     * - delay(1000) pour attendre 1 seconde
     * - _isAutoRefreshEnabled.value pour lire la valeur
     */
    private fun startAutoRefresh() {
        // TODO: Implémentez cette fonction
    }

    /**
     * TODO 5: Implémentez la fonction fetchUsers()
     *
     * Cette fonction doit :
     * - Lancer une coroutine avec viewModelScope.launch { }
     * - Mettre l'état à Loading au début
     * - Utiliser un bloc try-catch pour gérer les erreurs
     * - Dans le try :
     *   - Appeler apiService.getRandomUsers(5)
     *   - Mettre l'état à Success avec les résultats
     * - Dans le catch :
     *   - Mettre l'état à Error avec le message d'erreur
     *
     * Hints:
     * - viewModelScope.launch { } pour lancer une coroutine
     * - _uiState.value = UiState.Loading
     * - try { } catch (e: Exception) { }
     * - val response = apiService.getRandomUsers(5)
     * - _uiState.value = UiState.Success(response.results)
     * - _uiState.value = UiState.Error(e.message ?: "Erreur inconnue")
     */
    fun fetchUsers() {
        // TODO: Implémentez cette fonction
    }

    /**
     * TODO 6: Implémentez la fonction toggleAutoRefresh()
     *
     * Cette fonction doit inverser la valeur de _isAutoRefreshEnabled
     *
     * Hint: _isAutoRefreshEnabled.value = !_isAutoRefreshEnabled.value
     */
    fun toggleAutoRefresh() {
        // TODO: Implémentez cette fonction
    }
}
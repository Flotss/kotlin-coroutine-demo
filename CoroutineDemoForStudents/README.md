# kotlin-coroutine-demo

Une application Android de démonstration pour apprendre et explorer les coroutines Kotlin avec Jetpack Compose.

## 📋 À propos

Ce projet démontre l'utilisation pratique des coroutines Kotlin dans une application Android moderne. L'application récupère des utilisateurs aléatoires depuis l'API [randomuser.me](https://randomuser.me/) et les affiche avec un système de rafraîchissement automatique.

## 🚀 Technologies utilisées

- **Kotlin** - 100% du code
- **Jetpack Compose** - UI déclarative moderne
- **Coroutines Kotlin** - Gestion de l'asynchronisme
- **ViewModel** - Architecture MVVM
- **StateFlow** - Gestion d'état réactive
- **Retrofit** - Appels réseau
- **Gson** - Parsing JSON

## ✨ Fonctionnalités

- 📋 Affichage d'une liste d'utilisateurs aléatoires
- 🔄 Rafraîchissement automatique toutes les 30 secondes
- ⏱️ Compteur en temps réel avant le prochain refresh
- 🔘 Activation/désactivation du refresh automatique
- 🔄 Bouton de rafraîchissement manuel
- 📊 Gestion des états (Chargement, Succès, Erreur)

## 📚 Concepts de coroutines abordés

Ce projet démontre les concepts suivants :

### 1. **Fonctions suspendables (`suspend fun`)**
```kotlin
suspend fun getRandomUsers(@Query("results") results: Int = 5): UserResponse
```

### 2. **viewModelScope.launch**
Lancement de coroutines liées au cycle de vie du ViewModel :
```kotlin
viewModelScope.launch {
    _uiState.value = UiState.Loading
    try {
        val response = apiService.getRandomUsers(5)
        _uiState.value = UiState.Success(response.results)
    } catch (e: Exception) {
        _uiState.value = UiState.Error(e.message ?: "Erreur inconnue")
    }
}
```

### 3. **delay() - Délais asynchrones**
```kotlin
for (i in 30 downTo 1) {
    _timeUntilRefresh.value = i
    delay(1000) // Pause d'1 seconde sans bloquer le thread
}
```

### 4. **StateFlow et MutableStateFlow**
Gestion d'état réactive compatible avec Compose :
```kotlin
private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
val uiState: StateFlow<UiState> = _uiState.asStateFlow()
```

### 5. **collectAsState() dans Compose**
Observation des Flow dans l'UI :
```kotlin
val uiState by viewModel.uiState.collectAsState()
val timeUntilRefresh by viewModel.timeUntilRefresh.collectAsState()
```

### 6. **Gestion des erreurs avec try/catch**
Gestion des exceptions dans les coroutines pour les appels réseau.

### 7. **Boucle infinie avec coroutines**
Auto-refresh en arrière-plan :
```kotlin
while (true) {
    if (_isAutoRefreshEnabled.value) {
        fetchUsers()
        // Compteur de 30 secondes
        for (i in 30 downTo 1) {
            _timeUntilRefresh.value = i
            delay(1000)
        }
    }
}
```

## 📦 Installation

```bash
# Cloner le repository
git clone https://github.com/Flotss/kotlin-coroutine-demo.git

# Naviguer dans le dossier
cd kotlin-coroutine-demo
```

## 💻 Utilisation

1. Ouvrir le projet dans Android Studio
2. Synchroniser Gradle
3. Lancer l'application sur un émulateur ou appareil physique

## 🏗️ Structure du projet

```
app/src/main/java/com/isep/coroutineDemo/
├── MainActivity.kt           # UI avec Jetpack Compose
├── viewmodel/
│   └── UserViewModel.kt     # Logique métier et coroutines
├── network/
│   └── ApiService.kt        # Interface Retrofit
├── data/
│   └── User.kt              # Modèles de données
└── ui/theme/                # Thème Material Design
```

## 🎯 Points d'apprentissage

- ✅ Comment utiliser `suspend fun` avec Retrofit
- ✅ Lancer des coroutines dans un ViewModel
- ✅ Gérer l'état avec StateFlow
- ✅ Créer des délais asynchrones avec `delay()`
- ✅ Observer les Flow dans Jetpack Compose
- ✅ Gérer les erreurs dans les coroutines
- ✅ Implémenter un système de rafraîchissement automatique

## 🔗 API utilisée

[Random User Generator API](https://randomuser.me/) - API gratuite qui génère des données d'utilisateurs aléatoires.

## 📖 Ressources utiles

- [Documentation officielle Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Guide des coroutines](https://kotlinlang.org/docs/coroutines-guide.html)
- [StateFlow et SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)

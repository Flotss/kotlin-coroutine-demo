# 🎓 Exercice Pratique - Coroutines & Jetpack Compose

## 📚 Objectif Pédagogique

Ce projet est un exercice pratique pour apprendre à utiliser les **Coroutines Kotlin** dans une application Android avec **Jetpack Compose**. Vous allez implémenter un système d'auto-refresh automatique qui récupère des utilisateurs depuis une API.

## 🎯 Compétences Visées

- ✅ Comprendre et utiliser les **StateFlow** pour gérer l'état réactif
- ✅ Lancer des **coroutines** avec `viewModelScope.launch`
- ✅ Utiliser `delay()` pour créer des compteurs et des timers
- ✅ Gérer les **appels API asynchrones** avec try-catch
- ✅ Observer les StateFlow dans Compose avec `collectAsState()`
- ✅ Gérer les **états UI** (Loading, Success, Error)

## 📁 Structure du Projet

### Fichiers Complets (NE PAS MODIFIER)
- `MainActivity.kt` - Version complète de référence
- `UserViewModel.kt` - Version complète de référence
- `data/User.kt` - Classes de données
- `network/ApiService.kt` - Service API Retrofit

### Fichiers Éducatifs (À COMPLÉTER)
- `MainActivityEdu.kt` - **Votre fichier de travail pour l'UI**
- `viewmodel/UserViewModelEdu.kt` - **Votre fichier de travail pour la logique**

## 🚀 Comment Commencer

### Étape 1 : Comprendre le Code Existant

1. **Examinez les fichiers complets** (`MainActivity.kt` et `UserViewModel.kt`) pour voir le résultat final
2. **Lisez les commentaires** dans les fichiers "Edu" pour comprendre ce qui doit être fait
3. **Consultez la documentation** des concepts clés en bas de chaque fichier

### Étape 2 : Implémenter UserViewModelEdu.kt

Complétez les TODOs dans l'ordre :

#### TODO 2 & 3 : Déclarer les StateFlow
```kotlin
private val _timeUntilRefresh = MutableStateFlow(30)
val timeUntilRefresh: StateFlow<Int> = _timeUntilRefresh.asStateFlow()

private val _isAutoRefreshEnabled = MutableStateFlow(true)
val isAutoRefreshEnabled: StateFlow<Boolean> = _isAutoRefreshEnabled.asStateFlow()
```

#### TODO 4 : Initialiser l'auto-refresh
```kotlin
init {
    startAutoRefresh()
}
```

#### TODO 5 : Implémenter startAutoRefresh()
```kotlin
private fun startAutoRefresh() {
    viewModelScope.launch {
        while (true) {
            if (_isAutoRefreshEnabled.value) {
                fetchUsers()
                
                for (i in 30 downTo 1) {
                    _timeUntilRefresh.value = i
                    delay(1000)
                    
                    if (!_isAutoRefreshEnabled.value) break
                }
            } else {
                delay(1000)
            }
        }
    }
}
```

#### TODO 6 : Implémenter fetchUsers()
```kotlin
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
```

#### TODO 7 : Implémenter toggleAutoRefresh()
```kotlin
fun toggleAutoRefresh() {
    _isAutoRefreshEnabled.value = !_isAutoRefreshEnabled.value
}
```

### Étape 3 : Implémenter MainActivityEdu.kt

#### TODO 1 : Observer les StateFlow
```kotlin
val uiState by viewModel.uiState.collectAsState()
val timeUntilRefresh by viewModel.timeUntilRefresh.collectAsState()
val isAutoRefreshEnabled by viewModel.isAutoRefreshEnabled.collectAsState()
```

#### TODO 2 : Afficher le temps
```kotlin
Text(
    text = "Prochain refresh dans: ${timeUntilRefresh}s",
    // ... reste du code
)
```

#### TODO 3 : Icône dynamique
```kotlin
Icon(
    imageVector = if (isAutoRefreshEnabled) 
        Icons.Default.Timer 
    else 
        Icons.Default.Refresh,
    contentDescription = "Toggle Auto Refresh",
    tint = if (isAutoRefreshEnabled) 
        MaterialTheme.colorScheme.primary 
    else 
        MaterialTheme.colorScheme.onSecondaryContainer
)
```

#### TODO 4 : Gérer les états UI
```kotlin
when (val state = uiState) {
    is UiState.Loading -> {
        // Afficher le loading
    }
    is UiState.Success -> {
        UserList(users = state.users)
    }
    is UiState.Error -> {
        // Afficher l'erreur
    }
}
```

### Étape 4 : Tester Votre Code

1. **Modifiez le AndroidManifest.xml** pour utiliser `MainActivityEdu` :
```xml
<activity
    android:name=".MainActivityEdu"
    ...>
```

2. **Lancez l'application** et vérifiez que :
   - Les utilisateurs se chargent automatiquement
   - Le compteur décrémente de 30 à 1 seconde par seconde
   - Le bouton toggle active/désactive l'auto-refresh
   - Le bouton refresh manuel fonctionne
   - Les erreurs sont gérées correctement

## 🔍 Concepts Clés à Comprendre

### StateFlow vs LiveData
- **StateFlow** : Alternative moderne à LiveData, plus puissante
- **Émission de valeurs** : `.value =` pour émettre une nouvelle valeur
- **Observation** : `collectAsState()` dans Compose

### Coroutines
- **viewModelScope** : Scope lié au cycle de vie du ViewModel
- **launch** : Lance une coroutine sans retour
- **delay()** : Suspend la coroutine sans bloquer le thread

### Gestion des États
- **sealed class** : Pattern pour représenter des états exclusifs
- **when** : Pattern matching type-safe
- **Loading/Success/Error** : Pattern standard pour les opérations asynchrones

## 📊 Diagramme de Flux

```
┌─────────────────────┐
│   MainActivity      │
│   (UI/Compose)      │
└──────────┬──────────┘
           │ collectAsState()
           │
┌──────────▼──────────┐
│   UserViewModel     │
│   (Logique)         │
└──────────┬──────────┘
           │ launch + try/catch
           │
┌──────────▼──────────┐
│   ApiService        │
│   (Retrofit)        │
└──────────┬──────────┘
           │
┌──────────▼──────────┐
│   API Externe       │
│   (randomuser.me)   │
└─────────────────────┘
```

## ✅ Checklist de Validation

Avant de considérer l'exercice comme terminé, vérifiez :

### UserViewModelEdu.kt
- [ ] Les 3 StateFlow sont déclarés correctement
- [ ] `init` appelle `startAutoRefresh()`
- [ ] `startAutoRefresh()` contient une boucle infinie
- [ ] Le compteur va de 30 à 1 avec `delay(1000)`
- [ ] `fetchUsers()` gère les états Loading/Success/Error
- [ ] `toggleAutoRefresh()` inverse la valeur booléenne

### MainActivityEdu.kt
- [ ] Les 3 StateFlow sont observés avec `collectAsState()`
- [ ] Le temps est affiché dans le texte
- [ ] L'icône change selon `isAutoRefreshEnabled`
- [ ] Le `when` gère les 3 états (Loading, Success, Error)
- [ ] `UserList()` est appelé dans le cas Success

### Test Fonctionnel
- [ ] L'app démarre et charge automatiquement des utilisateurs
- [ ] Le compteur décrémente toutes les secondes
- [ ] Après 30 secondes, de nouveaux utilisateurs sont chargés
- [ ] Le bouton toggle stoppe/reprend l'auto-refresh
- [ ] Le bouton refresh manuel fonctionne
- [ ] Les erreurs réseau sont affichées correctement

## 🎁 Bonus (Optionnel)

Si vous avez terminé l'exercice, essayez ces améliorations :

1. **Modifier le délai** : Changez de 30 à 60 secondes
2. **Ajouter un bouton** : Permettre de choisir le nombre d'utilisateurs (5, 10, 20)
3. **Persister l'état** : Utiliser DataStore pour sauvegarder `isAutoRefreshEnabled`
4. **Animation** : Ajouter une animation lors du refresh
5. **Tests unitaires** : Écrire des tests pour le ViewModel

## 📖 Ressources

- [Documentation Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [StateFlow Documentation](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
- [Jetpack Compose State](https://developer.android.com/jetpack/compose/state)
- [ViewModel Documentation](https://developer.android.com/topic/libraries/architecture/viewmodel)

## ❓ FAQ

### Q: Pourquoi utiliser StateFlow plutôt que LiveData ?
**R:** StateFlow est plus moderne, fonctionne mieux avec Compose, et offre plus de flexibilité avec les opérateurs de Flow.

### Q: Que fait `delay(1000)` exactement ?
**R:** Elle suspend la coroutine pendant 1000ms sans bloquer le thread. Le thread peut exécuter d'autres tâches pendant ce temps.

### Q: Pourquoi utiliser `viewModelScope` ?
**R:** Il gère automatiquement l'annulation des coroutines quand le ViewModel est détruit, évitant les fuites mémoire.

### Q: Que signifie `by` dans `val uiState by viewModel.uiState.collectAsState()` ?
**R:** C'est la délégation de propriété Kotlin. Elle "déstructure" le State pour accéder directement à sa valeur.

## 🏆 Bonne Chance !

---


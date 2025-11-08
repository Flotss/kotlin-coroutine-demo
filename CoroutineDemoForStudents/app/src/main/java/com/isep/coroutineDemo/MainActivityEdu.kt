package com.isep.coroutineDemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.isep.coroutineDemo.data.User
import com.isep.coroutineDemo.ui.theme.CoroutineDemoTheme
import com.isep.coroutineDemo.viewmodel.UiState
import com.isep.coroutineDemo.viewmodel.UserViewModelEdu

/**
 * ==========================================
 * 🎓 EXERCICE PRATIQUE - COMPOSE & COROUTINES
 * ==========================================
 *
 * Objectifs d'apprentissage :
 * 1. Comprendre collectAsState() pour observer les StateFlow
 * 2. Utiliser les states dans Compose de manière réactive
 * 3. Gérer les différents états de l'UI (Loading, Success, Error)
 * 4. Comprendre la réactivité de Compose avec les coroutines
 */

class MainActivityEdu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserListScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserViewModelEdu = viewModel()) {
    // TODO 1: Observez les StateFlow du ViewModel avec collectAsState()
    // Hint: val uiState by viewModel.uiState.collectAsState()

    // TODO: Décommentez et complétez ces lignes
    // val uiState by viewModel.uiState.collectAsState()
    // val timeUntilRefresh by viewModel.timeUntilRefresh.collectAsState()
    // val isAutoRefreshEnabled by viewModel.isAutoRefreshEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Démo Coroutines & API - EDU") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Timer Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Timer",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // TODO 2: Affichez le temps restant avant le prochain refresh
                        // Remplacez "??" par la variable timeUntilRefresh
                        Text(
                            text = "Prochain refresh dans: ??s",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    Row {
                        // TODO 3: Utilisez isAutoRefreshEnabled pour changer l'icône et la couleur
                        IconButton(onClick = { viewModel.toggleAutoRefresh() }) {
                            Icon(
                                // TODO: Changez l'icône selon isAutoRefreshEnabled
                                // Hint: if (isAutoRefreshEnabled) Icons.Default.Timer else Icons.Default.Refresh
                                imageVector = Icons.Default.Timer, // TODO: Modifier cette ligne
                                contentDescription = "Toggle Auto Refresh",
                                // TODO: Changez la couleur selon isAutoRefreshEnabled
                                tint = MaterialTheme.colorScheme.primary // TODO: Modifier cette ligne
                            )
                        }

                        IconButton(onClick = { viewModel.fetchUsers() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            // TODO 4: Gérez les différents états de l'UI avec un when
            // Décommentez et complétez le code ci-dessous

            /*
            when (val state = uiState) {
                is UiState.Loading -> {
                    // TODO: Affichez un indicateur de chargement
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Chargement des utilisateurs...")
                        }
                    }
                }

                is UiState.Success -> {
                    // TODO: Affichez la liste des utilisateurs
                    // Hint: Appelez UserList(users = state.users)
                }

                is UiState.Error -> {
                    // TODO: Affichez un message d'erreur avec un bouton pour réessayer
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "❌ Erreur",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.fetchUsers() }) {
                                Text("Réessayer")
                            }
                        }
                    }
                }
            }
            */

            // TODO: Supprimez ce texte temporaire une fois le when implémenté
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "TODO: Implémentez la gestion des états UI",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun UserList(users: List<User>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(users) { user ->
            UserCard(user = user)
        }
    }
}

@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name.fullName(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "📱 ${user.phone}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "📍 ${user.location.city}, ${user.location.country}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
package com.example.yum.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val user = viewModel.getUserOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            title = { Text("Profil") },
            actions = {
                TextButton(
                    onClick = {
                        viewModel.logout()
                    }
                ) {
                    Text("Logg ut")
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = { onNavigateBack() }
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tilbake")
                }
            }
        )
        Text("Email: ${user?.email ?: "Ukjent"}")
        Text("Phone: ${user?.phone ?: "Ukjent"}")
        Text("Opprettet: ${user?.createdAt ?: "Ukjent"}")
        Text("Sist innlogging: ${user?.lastSignInAt ?: "Ukjent"}")
    }
}

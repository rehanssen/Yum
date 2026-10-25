package com.example.yum.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yum.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
) {
    val scope = rememberCoroutineScope()
    val user = supabase.auth.currentUserOrNull()



    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TopAppBar(
            title = { Text("Profil") },
            actions = {
                TextButton(
                    onClick = {
                        scope.launch {
                            supabase.auth.signOut()
                        }
                    }
                ) {
                    Text("Logg ut")
                }
            }
        )
        Text("Email: ${user?.email ?: "Ukjent"}")
        Text("Phone: ${user?.phone ?: "Ukjent"}")
        Text("Opprettet: ${user?.createdAt ?: "Ukjent"}")
        Text("Sist innlogging: ${user?.lastSignInAt ?: "Ukjent"}")
    }
}

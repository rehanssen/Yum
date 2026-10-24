package com.example.yum.ui.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.yum.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(onUserRegistered: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Registrer")

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-post") }
            )

            TextField(
                value = password1,
                onValueChange = { password1 = it },
                label = { Text("Passord") },
                visualTransformation = PasswordVisualTransformation()

            )

            TextField(
                value = password2,
                onValueChange = { password2 = it },
                label = { Text("Gjenta passord") },
                visualTransformation = PasswordVisualTransformation()

            )

            Button(
                onClick = {
                    try {

                    scope.launch {
                        supabase.auth.signUpWith(Email) {
                            this.email = email
                            this.password = password1
                        }
                    }
                    onUserRegistered()
                    } catch (e: Exception) {
                        Log.e(
                            "Sign up error",
                            "Error while signing up $email",
                            e
                        )
                    }
                },
                enabled = (password1 == password2)
            ) {
                Text("Registrer")
            }
        }
    }
}
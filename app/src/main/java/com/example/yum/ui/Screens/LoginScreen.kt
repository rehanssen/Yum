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
fun LoginScreen(
//    onLoginSuccess: () -> Unit,
    onRegisterNewUser: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Login")

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-post") }
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Passord") },
                visualTransformation = PasswordVisualTransformation()

            )

            Button(
                onClick = {
                    scope.launch {
                        try {
                            Log.d("Login debug", "Login with e-mail ($email)")
                            supabase.auth.signInWith(Email) {
                                this.email = email
                                this.password = password
                            }

//                            onLoginSuccess()
                        } catch (e: Exception) {
                            Log.e(
                                "LoginError",
                                "Error during supabase login with email and password",
                                e
                            )
                        }
                    }
                }
            ) {
                Text("Login")
            }

            Button(
                onClick = {
                    onRegisterNewUser()
                }) {
                Text("Registrer ny bruker")
            }
        }
    }
    Text("Login screen")
}



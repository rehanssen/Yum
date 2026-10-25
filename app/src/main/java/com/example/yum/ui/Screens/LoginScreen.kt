package com.example.yum.ui.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.yum.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onRegisterNewUser: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visiblePassword by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    Scaffold { innerPadding ->
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
                label = { Text("E-post") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Passord") },
                trailingIcon = {
                    IconButton(onClick = { visiblePassword = !visiblePassword }) {
                        Icon(
                            imageVector = if (visiblePassword)
                                Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                            contentDescription = "Toggle password visibility",
                        )
                    }
                },
                visualTransformation = if (visiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions =
                    KeyboardOptions(
                        imeAction = ImeAction.Go
                    ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        keyboardController?.hide()
                        logIn(scope, email, password)
                    }
                )
            )

            Button(
                onClick = {
                    logIn(scope, email, password)
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

fun logIn(
    scope: CoroutineScope,
    email: String,
    password: String
) {
    scope.launch {
        try {
            Log.d("Login debug", "Login with e-mail ($email)")
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        } catch (e: Exception) {
            Log.e(
                "LoginError",
                "Error during supabase login with email and password",
                e
            )
        }
    }
}



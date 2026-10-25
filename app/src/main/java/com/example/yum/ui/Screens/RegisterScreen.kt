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
fun RegisterScreen(onUserRegistered: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var visiblePassword1 by remember { mutableStateOf(false) }
    var visiblePassword2 by remember { mutableStateOf(false) }


    val keyboardController = LocalSoftwareKeyboardController.current
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
                label = { Text("E-post") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            TextField(
                value = password1,
                onValueChange = { password1 = it },
                label = { Text("Passord") },
                trailingIcon = {
                    IconButton(onClick = { visiblePassword1 = !visiblePassword1 }) {
                        Icon(
                            imageVector = if (visiblePassword1) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                },
                visualTransformation = if (visiblePassword1) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)

            )

            TextField(
                value = password2,
                onValueChange = { password2 = it },
                label = { Text("Gjenta passord") },
                trailingIcon = {
                    IconButton(onClick = { visiblePassword2 = !visiblePassword2 }) {
                        Icon(
                            imageVector = if (visiblePassword2) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                },
                visualTransformation = if (visiblePassword2) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    keyboardController?.hide()
                    registerUser(scope, email, password1, onUserRegistered)
                })
            )

            Button(
                onClick = {
                    registerUser(scope, email, password1, onUserRegistered)
                },
                enabled = (password1 == password2)
            ) {
                Text("Registrer")
            }
        }
    }
}

fun registerUser(
    scope: CoroutineScope,
    email: String,
    password: String,
    eventCallback: () -> Unit
) {
    try {

        scope.launch {
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        }
        eventCallback()
    } catch (e: Exception) {
        Log.e(
            "Sign up error",
            "Error while signing up $email",
            e
        )
    }
}
package com.example.yum.ui.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onUserRegistered: () -> Unit,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
//    var email by remember { mutableStateOf("") }
//    var password1 by remember { mutableStateOf("") }
//    var password2 by remember { mutableStateOf("") }
//    var visiblePassword1 by remember { mutableStateOf(false) }
//    var visiblePassword2 by remember { mutableStateOf(false) }

    var email = viewModel.email
    var password = viewModel.password
    var confirmPassword = viewModel.confirmPassword

    var visiblePassword = viewModel.visiblePassword
    var visibleConfirmPassword = viewModel.visibleConfirmPassword


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
                value = password,
                onValueChange = { viewModel.password = it },
                label = { Text("Passord") },
                trailingIcon = {
                    IconButton(onClick = { viewModel.visiblePassword = !visiblePassword }) {
                        Icon(
                            imageVector = if (viewModel.visiblePassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                },
                visualTransformation = if (visiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)

            )

            TextField(
                value = confirmPassword,
                onValueChange = { viewModel.confirmPassword = it },
                label = { Text("Gjenta passord") },
                trailingIcon = {
                    IconButton(onClick = { viewModel.visibleConfirmPassword = !visibleConfirmPassword }) {
                        Icon(
                            imageVector = if (visibleConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                },
                visualTransformation = if (visibleConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    keyboardController?.hide()
                    viewModel.signUp(email, password)
                })
            )

            Button(
                onClick = {
                    viewModel.signUp(email, password)
                },
                enabled = (password != "" && password == confirmPassword)
            ) {
                Text("Registrer")
            }
        }
    }
}
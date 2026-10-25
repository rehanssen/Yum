package com.example.yum.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yum.ui.Screens.LoginScreen
import com.example.yum.ui.Screens.RegisterScreen

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(onRegisterNewUser = {
                navController.navigate("register_user")
            })
        }

        composable("register_user") {
            RegisterScreen(onUserRegistered = {
                navController.popBackStack()
            })
        }
    }
}
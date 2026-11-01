package com.example.yum.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yum.ui.screens.LoginScreen
import com.example.yum.ui.screens.RegisterScreen
import io.github.jan.supabase.SupabaseClient

@Composable
fun AuthNavigation(
    supabase: SupabaseClient
) {
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
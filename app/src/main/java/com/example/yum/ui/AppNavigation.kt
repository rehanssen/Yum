package com.example.yum.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yum.ui.Screens.CreateRecipeScreen
import com.example.yum.ui.Screens.HomeScreen
import com.example.yum.ui.Screens.ProfileScreen
import com.example.yum.ui.Screens.RecipeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = { navController.navigate("home") },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Hjem"
                        )
                    },
                    label = { Text("Hjem") }
                )

                NavigationBarItem(
                    selected = currentRoute == "create_recipe",
                    onClick = { navController.navigate("create_recipe") },
                    icon = {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Ny oppskrift"
                        )
                    },
                    label = { Text("Ny oppskrift") }
                )

                NavigationBarItem(
                    selected = currentRoute == "profile",
                    onClick = { navController.navigate("profile") },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profil"
                        )
                    },
                    label = { Text("Profil") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .padding(innerPadding),
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            composable("home") {
                HomeScreen(
                    onRecipeClick = { recipeId ->
                        navController.navigate("recipe/$recipeId")
                    },
                    onCreateRecipe = {
                        navController.navigate("create_recipe")
                    })
            }

            composable("recipe/{recipeId}") { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId")

                if (recipeId == null) {
                    navController.popBackStack()
                } else {
                    RecipeScreen(recipeId = recipeId)
                }
            }

            composable("create_recipe") {
                CreateRecipeScreen(onSaveRecipe = {
                    navController.popBackStack()
                })
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}

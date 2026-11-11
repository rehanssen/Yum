package com.example.yum.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yum.ui.screens.CreateRecipeScreen
import com.example.yum.ui.screens.CreateRecipeScreenViewModel
import com.example.yum.ui.screens.HomeScreen
import com.example.yum.ui.screens.ProfileScreen
import com.example.yum.ui.screens.RecipeScreen
import io.github.jan.supabase.SupabaseClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    supabase: SupabaseClient
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route
    val createRecipeViewModel: CreateRecipeScreenViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            when (currentRoute) {
                "recipe/{recipeId}" -> {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Tilbake"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    )

                }

                "create_recipe" -> {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Tilbake"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                createRecipeViewModel.saveRecipe()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = "Lagre"
                                )
                            }
                        }
                    )
                }
            }
        },

        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
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

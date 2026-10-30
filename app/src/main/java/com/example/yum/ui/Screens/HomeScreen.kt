package com.example.yum.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yum.Model.Recipe
import com.example.yum.supabase
import com.example.yum.ui.RecipeLoadingState
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (String) -> Unit,
    onCreateRecipe: () -> Unit,
    viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModel.Factory
    )
) {
    val user = supabase.auth.currentUserOrNull()
    val scope = rememberCoroutineScope()

    val loadingState: RecipeLoadingState<List<Recipe>> = viewModel.recipes

    LaunchedEffect(Unit) {
        viewModel.loadRecipes()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Home screen")
                        Column {
                            Text(user?.email ?: "Anon")
                            Button(
                                onClick = {
                                    scope.launch {
                                        supabase.auth.signOut()
                                    }
                                }
                            ) {
                                Text("Logg ut")
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (loadingState) {
                is RecipeLoadingState.Loading -> {
                    CircularProgressIndicator()
                }
                is RecipeLoadingState.Failed -> {
                    Text(loadingState.message)
                }
                is RecipeLoadingState.Loaded -> {
                    LazyColumn {
                        items(loadingState.data) { recipe ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                onClick = {
                                    onRecipeClick(recipe.id!!)
                                }
                            ) {
                            Text(recipe.title)
                                Text("  ${recipe.description}")
                            }
                        }
                    }
                }
            }
        }
    }
}

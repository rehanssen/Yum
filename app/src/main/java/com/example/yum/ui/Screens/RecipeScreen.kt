package com.example.yum.ui.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yum.Data.RecipeRepository
import com.example.yum.ui.RecipeLoadingState

@Composable
fun RecipeScreen(
    recipeId: String,
    viewModel: RecipeScreenViewModel = viewModel(
        factory = RecipeScreenViewModel.Factory
    )
) {
    val recipeLoadingState = viewModel.recipe

    LaunchedEffect(Unit) {
        viewModel.loadRecipe(recipeId)
    }

    when (recipeLoadingState) {
        is RecipeLoadingState.Loading -> {
            CircularProgressIndicator()
        }

        is RecipeLoadingState.Failed -> {
            Text(recipeLoadingState.message)
        }

        is RecipeLoadingState.Loaded -> {

            LazyColumn {
                item {
                    Text(
                        textAlign = TextAlign.Center,
                        text = recipeLoadingState.data.title
                    )
                }

                item {
                    Text("Ingredienser")
                }

                items(recipeLoadingState.data.recipeIngredients) { ingredient ->
                    Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")
                }


                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text("Fremgangsmåte")
                }

                itemsIndexed(recipeLoadingState.data.recipeSteps.sortedBy { it.sortOrder }) { index, step ->
                    Text("${index + 1}: ${step.instruction ?: "Gjør hva som helst"}")
                }
            }

        }
    }
}
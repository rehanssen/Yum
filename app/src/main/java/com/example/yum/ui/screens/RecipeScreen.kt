package com.example.yum.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yum.model.Recipe
import com.example.yum.model.RecipeIngredient
import com.example.yum.model.RecipeStep
import com.example.yum.ui.RecipeLoadingState

@Composable
fun RecipeScreen(
    recipeId: String,
    viewModel: RecipeScreenViewModel = hiltViewModel()
) {
    val recipeLoadingState = viewModel.recipe
    val ingredientsVisible = viewModel.ingredientsVisible

    LaunchedEffect(Unit) {
        viewModel.loadRecipe(recipeId)
    }

    when (recipeLoadingState) {
        is RecipeLoadingState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is RecipeLoadingState.Failed -> {
            Text(recipeLoadingState.message)
        }

        is RecipeLoadingState.Loaded -> {
            BoxWithConstraints {
                val wideScreen = maxWidth >= 640.dp

                if (wideScreen) {
                    RecipeWideScreenDisplay(recipeLoadingState.data)
                } else {
                    RecipeSmallScreenDisplay(
                        recipe = recipeLoadingState.data,
                        ingredientsVisible = ingredientsVisible,
                        onToggleIngredients = { viewModel.toggleIngredientPanel() }
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeWideScreenDisplay(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item { Text("Ingredienser") }
            items(recipe.recipeIngredients) { ingredient ->
                Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")
            }
        }

        LazyColumn(
            modifier = Modifier.weight(2f)
        ) {
            item { Text("Fremgangsmåte") }
            itemsIndexed(recipe.recipeSteps.sortedBy { it.sortOrder }) { index, step ->
                Text("${index + 1}: ${step.instruction ?: "Gjør hva som helst"}")
            }
        }
    }
}

@Composable
fun RecipeSmallScreenDisplay(
    recipe: Recipe,
    ingredientsVisible: Boolean,
    onToggleIngredients: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box() {
        // Steps
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 48.dp)
        ) {
            item { Text("Fremgangsmåte") }
            itemsIndexed(recipe.recipeSteps.sortedBy { it.sortOrder }) { index, step ->
                Text("${index + 1}: ${step.instruction ?: "Gjør hva som helst"}")
            }
        }

            IngredientsPanel(
                ingredients = recipe.recipeIngredients,
                ingredientsVisible = ingredientsVisible,
                onToggleVisibility = onToggleIngredients
            )
    }
}

@Composable
fun IngredientsPanel(
    modifier: Modifier = Modifier,
    ingredients: List<RecipeIngredient>,
    ingredientsVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    AnimatedContent(
        targetState = ingredientsVisible,
        transitionSpec = {
            if (targetState) {
                slideInHorizontally { -it } togetherWith
                        slideOutHorizontally { it }
            } else {
                slideInHorizontally { it } togetherWith
                        slideOutHorizontally { -it }
            }
        },
        label = "Ingredients panel"
    ) { visible ->
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(if (visible) 0.7f else 0.1f)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            if (visible) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item { Text("Ingredienser") }
                    items(ingredients) { ingredient ->
                        Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")
                    }
                }
            }

            IconButton(
                onClick = onToggleVisibility,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (visible) Icons.Default.KeyboardArrowLeft
                    else Icons.Default.KeyboardArrowRight,
                    contentDescription = "Vis/skjul ingredienser"
                )
            }
        }
    }
}
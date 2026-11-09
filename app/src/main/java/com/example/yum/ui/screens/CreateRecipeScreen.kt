package com.example.yum.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yum.model.DraftRecipeIngredient
import com.example.yum.model.Recipe
import com.example.yum.model.RecipeIngredient
import com.example.yum.model.RecipeStep

@Composable
fun CreateRecipeScreen(
    onSaveRecipe: () -> Unit,
    viewModel: CreateRecipeScreenViewModel = hiltViewModel()
) {
    val id: String? = null

    val ownerId: String

    val visibility: String = "private"


    val yieldUnit: String = "porsjoner"

    val createdAt: String? = null

    val updatedAt: String? = null

    val recipe = Recipe
    val ingredients = mutableListOf<RecipeIngredient>()
    val steps = mutableListOf<RecipeStep>()


    var title = viewModel.title
    var description = viewModel.description
    var yieldQuantity = viewModel.yieldQuantity

    BoxWithConstraints {
        val wideScreen = maxWidth >= 640.dp

        if (wideScreen) {
            WideScreenCreateRecipe(viewModel)
        } else {
//            SmallScreenCreateRecipe()
//            RecipeSmallScreenDisplay(
//                recipe = recipeLoadingState.data,
//                ingredientsVisible = ingredientsVisible,
//                onToggleIngredients = { viewModel.toggleIngredientPanel() }
//            )
        }
    }
}

@Composable
fun WideScreenCreateRecipe(
    viewModel: CreateRecipeScreenViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            item { Text("Ingredienser") }
            items(viewModel.ingredients) { ingredient ->
                Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")
            }

            item {
                Row {
                    OutlinedTextField(
                        value = viewModel.ingredientInput.quantity,
                        onValueChange = {
                            viewModel.updateIngredientInput(
                                viewModel.ingredientInput.copy(quantity = it)
                            )
                        },
                        label = { Text("Antall") },
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = viewModel.ingredientInput.unit,
                        onValueChange = {
                            viewModel.updateIngredientInput(
                                viewModel.ingredientInput.copy(unit = it)
                            )
                        },
                        label = { Text("Enhet") },
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = viewModel.ingredientInput.name,
                        onValueChange = {
                            viewModel.updateIngredientInput(
                                viewModel.ingredientInput.copy(name = it)
                            )
                        },
                        label = { Text("Ingrediens") },
                        modifier = Modifier.weight(2f)
                    )

                    IconButton(onClick = viewModel::addIngredient) {
                        Icon(Icons.Default.Add, contentDescription = "Legg til")
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(2f)
        ) {
            item {
                OutlinedTextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.title = it },
                    label = { Text("Tittel") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = viewModel.description,
                    onValueChange = { viewModel.description = it },
                    label = { Text("Beskrivelse") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Text("Fremgangsmåte")

            }
        }
    }
}

package com.example.yum.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yum.model.DraftRecipeIngredient
import com.example.yum.model.DraftRecipeStep
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

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    BoxWithConstraints {
        val wideScreen = maxWidth >= 640.dp

        if (wideScreen) {
            WideScreenCreateRecipe(viewModel, keyboardController, focusRequester)
        } else {
            SmallScreenCreateRecipe(viewModel, keyboardController, focusRequester)
        }
    }
}

@Composable
fun WideScreenCreateRecipe(
    viewModel: CreateRecipeScreenViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester
) {
    LaunchedEffect(viewModel.showNewStepField) {
        if (viewModel.showNewStepField) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")

                    IconButton(
                        onClick = { viewModel.deleteIngredient(ingredient) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Slett rad"
                        )
                    }
                }
            }

            item {
                IconButton(onClick = viewModel::openAddIngredientPanel) {
                    Icon(Icons.Default.Add, contentDescription = "Legg til")
                }
            }
        }


        LazyColumn(
            modifier = Modifier
                .weight(2f)
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.title,
                    onValueChange = { viewModel.title = it },
                    label = { Text("Tittel") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.description,
                    onValueChange = { viewModel.description = it },
                    label = { Text("Beskrivelse") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )
            }

            item {
                Text("Fremgangsmåte")
            }

            itemsIndexed(viewModel.steps.sortedBy { it.sortOrder }) { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${index + 1}: ${step.instruction}")
                    IconButton(
                        onClick = {
                            viewModel.deleteStep(step)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Slett")
                    }
                }
            }

            item {
                if (viewModel.showNewStepField) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = viewModel.stepInput.instruction,
                        onValueChange = {
                            viewModel.updateStepInput(
                                viewModel.stepInput.copy(instruction = it)
                            )
                        },
                        label = { Text("Instruksjon") },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.showNewStepField = false
                                    viewModel.stepInput = DraftRecipeStep()
                                }
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Avbryt")
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            viewModel.addStep()
                            viewModel.showNewStepField = false
                            keyboardController?.hide()
                        })
                    )
                } else {
                    IconButton(
                        onClick = { viewModel.showNewStepField = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Legg til steg"
                        )
                    }
                }
            }
        }
    }


    if (viewModel.showAddIngredient) {
        NewIngredientOverlay(viewModel, keyboardController)
    }
}

@Composable
fun SmallScreenCreateRecipe(
    viewModel: CreateRecipeScreenViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester
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
            itemsIndexed(viewModel.steps.sortedBy { it.sortOrder }) { index, step ->
                Text("${index + 1}: ${step.instruction}")
            }
        }

        IngredientsSidePanel(
            viewModel = viewModel,
            keyboardController = keyboardController,
            ingredients = viewModel.ingredients,
            ingredientsVisible = viewModel.showAddIngredientSidepanel,
            onToggleVisibility = {
                viewModel.showAddIngredientSidepanel = !viewModel.showAddIngredientSidepanel
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewIngredientOverlay(
    viewModel: CreateRecipeScreenViewModel,
    keyboardController: SoftwareKeyboardController?
) {
    ModalBottomSheet(
        onDismissRequest = { viewModel.showAddIngredient = false }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Legg til ingrediens")

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.ingredientInput.name,
                    onValueChange = {
                        viewModel.updateIngredientInput(
                            viewModel.ingredientInput.copy(name = it)
                        )
                    },
                    label = { Text("Ingrediens") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = viewModel.ingredientInput.quantity,
                    onValueChange = {
                        viewModel.updateIngredientInput(
                            viewModel.ingredientInput.copy(quantity = it)
                        )
                    },
                    label = { Text("Mengde") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = viewModel.ingredientInput.unit,
                    onValueChange = {
                        viewModel.updateIngredientInput(
                            viewModel.ingredientInput.copy(unit = it)
                        )
                    },
                    label = { Text("Enhet") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }

            OutlinedTextField(
                value = viewModel.ingredientInput.notes,
                onValueChange = {
                    viewModel.updateIngredientInput(
                        viewModel.ingredientInput.copy(notes = it)
                    )
                },
                label = { Text("Notat") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    viewModel.addIngredient()
                    viewModel.closeAddIngredientPanel()
                    keyboardController?.hide()
                })
            )

            OutlinedButton(
                onClick = {
                    viewModel.addIngredient()
                    viewModel.closeAddIngredientPanel()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Lagre")
            }
        }
    }
}

@Composable
fun IngredientsSidePanel(
    viewModel: CreateRecipeScreenViewModel,
    keyboardController: SoftwareKeyboardController?,
    ingredients: List<DraftRecipeIngredient>,
    ingredientsVisible: Boolean,
    onToggleVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    val groupedIngredients = ingredients.groupBy { it.groupNumber }.toSortedMap()
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
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    item {
                        Text(
                            text = "Ingredienser",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    groupedIngredients.forEach { (group, groupIngredients) ->
                        item {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onPrimary,
                                thickness = 2.dp,
                            )
                        }

                        items(groupIngredients) { ingredient ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")
                                IconButton(onClick = { viewModel.deleteIngredient(ingredient) }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Slett"
                                    )
                                }
                            }
                        }
                    }

                    item {
                        if (viewModel.showAddIngredient) {
                            NewIngredientOverlay(
                                viewModel,
                                keyboardController
                            )
                        } else {
                            Button(onClick = { viewModel.showAddIngredient = true }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Legg til ingrediens"
                                )
                            }
                        }
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
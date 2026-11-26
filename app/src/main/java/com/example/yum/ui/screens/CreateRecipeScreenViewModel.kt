package com.example.yum.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.yum.data.RecipeRepository
import com.example.yum.model.CreateRecipeParams
import com.example.yum.model.DraftRecipeIngredient
import com.example.yum.model.DraftRecipeStep
import com.example.yum.model.IngredientInsert
import com.example.yum.model.RecipeIngredient
import com.example.yum.model.RecipeInsert
import com.example.yum.model.RecipeStep
import com.example.yum.model.StepInsert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecipeScreenViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    private var recipeId: String? = null
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var visibility by mutableStateOf("")
    var yieldQuantity: String by mutableStateOf("")
    var yieldUnit by mutableStateOf("")
    var active by mutableStateOf(true)

    val ingredients = mutableStateListOf<DraftRecipeIngredient>()
    var ingredientInput by mutableStateOf(DraftRecipeIngredient())
        private set
    val steps = mutableStateListOf<DraftRecipeStep>()
    var stepInput by mutableStateOf(DraftRecipeStep())

    var showAddIngredient by mutableStateOf(false)
    var showNewStepField by mutableStateOf(false)
    var showAddIngredientSidepanel by mutableStateOf(true)

    fun init(recipeId: String?) {
        if (recipeId != null) {
            this.recipeId = recipeId
            loadRecipe(recipeId)
        }
    }

    fun updateIngredientInput(input: DraftRecipeIngredient) {
        ingredientInput = input
    }

    fun addIngredient() {
        if (ingredientInput.name.isBlank()) return

        ingredients.add(ingredientInput)
        ingredientInput = DraftRecipeIngredient()
    }

    fun toggleShowAddIngredient() {
        showAddIngredient = !showAddIngredient
    }

    fun openAddIngredientPanel() {
        showAddIngredient = true
    }

    fun closeAddIngredientPanel() {
        showAddIngredient = false
    }

    fun toggleShowAddIngredientsSidepanel() {
        showAddIngredientSidepanel = !showAddIngredientSidepanel
    }

    fun openAddIngredientSidepanel() {
        showAddIngredientSidepanel = true
    }

    fun closeAddIngredientSidepanel() {
        showAddIngredientSidepanel = false
    }

    fun deleteIngredient(ingredient: DraftRecipeIngredient) {
        ingredients.remove(ingredient)
    }

    fun addStep() {
        if (stepInput.instruction.isBlank()) return

        steps.add(stepInput)
        stepInput = DraftRecipeStep()
    }

    fun updateStepInput(input: DraftRecipeStep) {
        stepInput = input
    }

    fun deleteStep(step: DraftRecipeStep) {
        steps.remove(step)
    }

    fun loadRecipe(id: String) {
        viewModelScope.launch {
            val recipe = repository.getRecipe(id)
            title = recipe.title
            description = recipe.description.toString()
            visibility = recipe.visibility
            ingredients.apply {
                clear()
                addAll(recipe.recipeIngredients.map { i ->
                    DraftRecipeIngredient(
                        i.name,
                        i.quantity?.toString() ?: "",
                        i.unit ?: "",
                        i.notes ?: "",
                        groupNumber = i.groupNumber
                    )
                })
            }
            steps.apply {
                clear()
                addAll(recipe.recipeSteps.map { s ->
                    DraftRecipeStep(
                        recipeId = s.recipeId,
                        sortOrder = s.sortOrder,
                        instruction = s.instruction.toString()
                    )
                })
            }
            yieldQuantity = recipe.yieldQuantity.toString()
            yieldUnit = recipe.yieldUnit
            active = recipe.active
        }

    }

    fun saveRecipe() {
        if (recipeId == null) {
            saveNewRecipe()
        } else {
            updateRecipe()
        }
    }

    private fun updateRecipe() {
        Log.d("Update recipe", "Updating recipe. title=$title, desc=$description")

    }

    private fun saveNewRecipe() {
        val recipeTitle = title
        val recipeDescription = description
        Log.d(
            "Save recipe", "Saving recipe. title=$recipeTitle, desc=$recipeDescription"
        )
        viewModelScope.launch {
            try {
                repository.createRecipe(
                    CreateRecipeParams(
                        recipe = RecipeInsert(
                            title = recipeTitle,
                            description = recipeDescription,
                            visibility = "private",
                            yieldQuantity = 4.0,
                            yieldUnit = "porsjoner",
                            active = active
                        ),
                        ingredients = ingredients.mapIndexed { index, ingredient ->
                            IngredientInsert(
                                name = ingredient.name,
                                quantity = ingredient.quantity.toDoubleOrNull(),
                                unit = ingredient.unit.ifBlank { null },
                                sortOrder = index,
                                notes = ingredient.notes.ifBlank { null }
                            )
                        },
                        steps = steps.mapIndexed { index, step ->
                            StepInsert(
                                instruction = step.instruction,
                                sortOrder = step.sortOrder
                            )
                        }
                    )
                )
            } catch (e: Exception) {
                Log.e(
                    "CreateRecipe",
                    "Error creating and uploading recipe to DB",
                    e
                )
            }
        }
    }
}
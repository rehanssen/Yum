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
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var yieldQuantity: String by mutableStateOf("4.0")
    var yieldUnit by mutableStateOf("porsjoner")

    val ingredients = mutableStateListOf<DraftRecipeIngredient>()
    var ingredientInput by mutableStateOf(DraftRecipeIngredient())
        private set
    val steps = mutableStateListOf<DraftRecipeStep>()
    var stepInput by mutableStateOf(DraftRecipeStep())

    var showAddIngredient by mutableStateOf(false)
    var showNewStepField by mutableStateOf(false)
    var showAddIngredientSidepanel by mutableStateOf(true)

    fun updateIngredientInput(input: DraftRecipeIngredient) {
        ingredientInput = input
    }

    fun addIngredient() {
        if (ingredientInput.name.isBlank()) return

        ingredients.add(ingredientInput)
        ingredientInput = DraftRecipeIngredient()
    }

    fun openAddIngredientPanel() {
        showAddIngredient = true
    }

    fun closeAddIngredientPanel() {
        showAddIngredient = false
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

    fun saveRecipe() {
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
                            yieldUnit = "porsjoner"
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


//
//    var title: String by mutableStateOf("")
//    var description: String by mutableStateOf("")
//    fun updateTitle(it: String) {
//        title = it
//    }
//
//    fun updateDescription(it: String) {
//        description = it
//    }
//
//    fun updateYieldQuantity(it: String) {
//        yieldQuantity = it
//    }


//    val id: String? = null
//
//    @SerialName("owner_id")
//    val ownerId: String
//
//    val title: String
//    val description: String? = null
//    val visibility: String = "private"
//
//    @SerialName("yield_quantity")
//    val yieldQuantity: Double = 4.0
//
//    @SerialName("yield_unit")
//    val yieldUnit: String = "porsjoner"
//
//    @SerialName("created_at")
//    val createdAt: String? = null
//
//    @SerialName("updated_at")
//    val updatedAt: String? = null
}
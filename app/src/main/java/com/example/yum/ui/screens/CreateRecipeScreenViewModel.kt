package com.example.yum.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.yum.model.DraftRecipeIngredient
import com.example.yum.model.RecipeIngredient
import com.example.yum.model.RecipeStep
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRecipeScreenViewModel @Inject constructor() : ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var yieldQuantity: String by mutableStateOf("4.0")
    var yieldUnit by mutableStateOf("porsjoner")

    val ingredients = mutableStateListOf<DraftRecipeIngredient>()
    var ingredientInput by mutableStateOf(DraftRecipeIngredient())
        private set
    val steps = mutableStateListOf<RecipeStep>()

    fun updateIngredientInput(input: DraftRecipeIngredient) {
        ingredientInput = input
    }

    fun addIngredient() {
        if (ingredientInput.name.isBlank()) return

        ingredients.add(ingredientInput)
        ingredientInput = DraftRecipeIngredient()
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
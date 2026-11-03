package com.example.yum.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.yum.data.RecipeRepository
import com.example.yum.model.Recipe
import com.example.yum.ui.RecipeLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    var recipe by mutableStateOf<RecipeLoadingState<Recipe>>(RecipeLoadingState.Loading)
        private set
    var ingredientsVisible by mutableStateOf(true)

    fun loadRecipe(id: String) {
        viewModelScope.launch {
            recipe = try {
                RecipeLoadingState.Loaded(repository.getRecipe(id))
            } catch (e: Exception) {
                Log.e("Loading error:", e.message ?: "Ukjent feil")
                RecipeLoadingState.Failed(e.message ?: "Ukjent feil")
            }
        }
    }

    fun toggleIngredientPanel() {
        ingredientsVisible = !ingredientsVisible
    }
}
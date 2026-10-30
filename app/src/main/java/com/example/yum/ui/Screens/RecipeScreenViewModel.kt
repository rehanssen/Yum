package com.example.yum.ui.Screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.yum.Data.RecipeRepository
import com.example.yum.Model.Recipe
import com.example.yum.ui.RecipeLoadingState
import kotlinx.coroutines.launch

class RecipeScreenViewModel(
//    recipeId: String,
    private val repository: RecipeRepository
) : ViewModel() {
    var recipe by mutableStateOf<RecipeLoadingState<Recipe>>(RecipeLoadingState.Loading)
        private set

    companion object {
        val Factory = viewModelFactory {
            initializer {
                RecipeScreenViewModel(repository = RecipeRepository())
            }
        }
    }

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
}
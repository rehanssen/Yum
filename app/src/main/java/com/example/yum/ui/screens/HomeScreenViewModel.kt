package com.example.yum.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yum.data.RecipeRepository
import com.example.yum.model.Recipe
import com.example.yum.ui.RecipeLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val supabase: SupabaseClient,
    val repository: RecipeRepository
) : ViewModel() {

    var recipes by mutableStateOf<RecipeLoadingState<List<Recipe>>>(RecipeLoadingState.Loading)
        private set

    fun loadRecipes() {
        viewModelScope.launch {
            recipes = try {
                RecipeLoadingState.Loaded(repository.getRecipes())
            } catch (e: Exception) {
                RecipeLoadingState.Failed(e.message ?: "Ukjent feil")
            }
        }
    }
}
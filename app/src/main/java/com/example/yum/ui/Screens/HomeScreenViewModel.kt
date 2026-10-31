package com.example.yum.ui.Screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.indirect.IndirectPointerEventPrimaryDirectionalMotionAxis
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.yum.Data.RecipeRepository
import com.example.yum.Model.Recipe
import com.example.yum.ui.RecipeLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseDsl
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    public val supabase: SupabaseClient,
    private val repository: RecipeRepository
) : ViewModel() {

//    companion object {
//        val Factory = viewModelFactory {
//            initializer {
//                HomeScreenViewModel(RecipeRepository())
//            }
//        }
//    }
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
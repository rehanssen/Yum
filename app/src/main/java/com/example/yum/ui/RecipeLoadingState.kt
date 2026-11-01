package com.example.yum.ui

sealed interface RecipeLoadingState<out T> {
    data object Loading : RecipeLoadingState<Nothing>
    data class Loaded<T>(val data: T) : RecipeLoadingState<T>
    data class Failed(val message: String) : RecipeLoadingState<Nothing>
}
package com.example.yum.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DraftRecipeStep(
    val recipeId: String = "",
    val sortOrder: Int = 0,
    val instruction: String = "",
)
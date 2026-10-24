package com.example.yum.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeStep(
    val id: String? = null,

    @SerialName("recipe_id")
    val recipeId: String,

    @SerialName("sort_order")
    val sortOrder: Int,

    val instruction: String? = null,

    @SerialName("linked_recipe_id")
    val linkedRecipeId: String? = null,

    @SerialName("linked_recipe_multiplier")
    val linkedRecipeMultiplier: Double = 1.0
)

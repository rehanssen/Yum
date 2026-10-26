package com.example.yum.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String? = null,

    @SerialName("owner_id")
    val ownerId: String,

    val title: String,
    val description: String? = null,
    val visibility: String = "private",

    @SerialName("recipe_ingredients")
    val recipeIngredients: List<RecipeIngredient>,
    @SerialName("recipe_steps")
    val recipeSteps: List<RecipeStep>,

    @SerialName("yield_quantity")
    val yieldQuantity: Double = 4.0,

    @SerialName("yield_unit")
    val yieldUnit: String = "porsjoner",

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null
)

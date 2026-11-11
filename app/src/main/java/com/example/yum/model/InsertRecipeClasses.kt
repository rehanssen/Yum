package com.example.yum.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeParams(
    @SerialName("p_recipe")
    val recipe: RecipeInsert,

    @SerialName("p_ingredients")
    val ingredients: List<IngredientInsert>,

    @SerialName("p_steps")
    val steps: List<StepInsert>
)

@Serializable
data class RecipeInsert(
    val title: String,
    val description: String?,
    val visibility: String,
    @SerialName("yield_quantity")
    val yieldQuantity: Double,
    @SerialName("yield_unit")
    val yieldUnit: String
)

@Serializable
data class IngredientInsert(
    val name: String,
    val quantity: Double?,
    val unit: String?,
    @SerialName("sort_order")
    val sortOrder: Int,
    val notes: String?
)

@Serializable
data class StepInsert(
    val instruction: String,
    @SerialName("sort_order")
    val sortOrder: Int
)
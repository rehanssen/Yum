package com.example.yum.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeIngredient(
    @SerialName("recipe_id")
    val recipeId: String,

    val name: String,
    val quantity: Double? = null,
    val unit: String? = null,

    @SerialName("sort_order")
    val sortOrder: Int = 0,

    val notes: String? = null,

    @SerialName("group_number")
    val groupNumber: Int = 0
)

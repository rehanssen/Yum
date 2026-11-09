package com.example.yum.model

import kotlinx.serialization.SerialName

data class DraftRecipeIngredient(
    var name: String = "",
    var quantity: String = "",
    var unit: String = "",

    var sortOrder: Int = 0,

    var notes: String? = null,

    var groupNumber: Int = 0
)
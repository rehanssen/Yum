package com.example.yum.model

import kotlinx.serialization.SerialName

data class DraftRecipeIngredient(
    var name: String = "",
    var quantity: String = "",
    var unit: String = "",

    var notes: String = "",

    var groupNumber: Int = 0
)
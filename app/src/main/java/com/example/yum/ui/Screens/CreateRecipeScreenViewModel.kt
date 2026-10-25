package com.example.yum.ui.Screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateRecipeScreenViewModel : ViewModel() {
    var title: String by mutableStateOf("")
    var description: String by mutableStateOf("")
    var yieldQuantity: String by mutableStateOf("4.0")
    fun updateTitle(it: String) {
        title = it
    }

    fun updateDescription(it: String) {
        description = it
    }

    fun updateYieldQuantity(it: String) {
        yieldQuantity = it
    }



//    val id: String? = null
//
//    @SerialName("owner_id")
//    val ownerId: String
//
//    val title: String
//    val description: String? = null
//    val visibility: String = "private"
//
//    @SerialName("yield_quantity")
//    val yieldQuantity: Double = 4.0
//
//    @SerialName("yield_unit")
//    val yieldUnit: String = "porsjoner"
//
//    @SerialName("created_at")
//    val createdAt: String? = null
//
//    @SerialName("updated_at")
//    val updatedAt: String? = null
}
package com.example.yum.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreateRecipeScreen(
    viewModel: CreateRecipeScreenViewModel = viewModel(),
    onSaveRecipe: () -> Unit
) {


    val id: String? = null

    val ownerId: String

    val visibility: String = "private"


    val yieldUnit: String = "porsjoner"

    val createdAt: String? = null

    val updatedAt: String? = null
    Column() {
        Text("Heia")
    }


    var title = viewModel.title
    var description = viewModel.description
    var yieldQuantity = viewModel.yieldQuantity

    Column(
        modifier = Modifier
            .fillMaxSize(),

    ) {
        TextField(
            value = title,
            onValueChange = { viewModel.updateTitle(it) },
            label = { Text("Tittel") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        TextField(
            value = description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text("Description") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        TextField(
            value = yieldQuantity,
            onValueChange = { viewModel.updateYieldQuantity(it) },
            label = { Text("Yield") },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        Button(onClick = onSaveRecipe) {
            Text("Lagre")
        }
    }
}
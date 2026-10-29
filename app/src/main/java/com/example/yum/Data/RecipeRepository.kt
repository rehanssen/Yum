package com.example.yum.Data

import android.util.Log
import com.example.yum.Model.Recipe
import com.example.yum.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class RecipeRepository(

) {
    suspend fun getRecipes() : List<Recipe> {
        Log.i("Data fetching:", "Fetching recipes")
        return supabase
            .from("recipes")
            .select()
            .decodeList<Recipe>()
    }

    suspend fun getRecipe(id: String) : Recipe {
        return supabase
            .from("recipes")
            .select(
                Columns.raw("""
                    *,
                    recipe_ingredients(*),
                    recipe_steps(*)
                """.trimIndent())
            ) {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<Recipe>()
    }
}
package com.example.yum.data

import android.util.Log
import com.example.yum.model.CreateRecipeParams
import com.example.yum.model.Recipe
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.rpc
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val supabase: SupabaseClient
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
                    recipe_steps!recipe_steps_recipe_id_fkey(*)
                """.trimIndent())
            ) {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<Recipe>()
    }

    suspend fun createRecipe(params: CreateRecipeParams) {
        supabase.postgrest.rpc(
            "create_recipe",
            params
        )
    }
}
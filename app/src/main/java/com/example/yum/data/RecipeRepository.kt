package com.example.yum.data

import android.util.Log
import com.example.yum.model.CreateRecipeParams
import com.example.yum.model.Recipe
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.rpc
import java.util.Locale.filter
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val supabase: SupabaseClient
) {
    private var recipeCache: List<Recipe>? = null
    suspend fun getRecipes() : List<Recipe> {
        recipeCache?.let { return it }

        Log.i("Data fetching:", "Fetching recipes")
        val recipes = supabase
            .from("recipes")
            .select() {
                filter {
                    eq("active", true)
                }
            }
            .decodeList<Recipe>()
        recipeCache = recipes
        return recipes
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

        recipeCache = null
    }
}
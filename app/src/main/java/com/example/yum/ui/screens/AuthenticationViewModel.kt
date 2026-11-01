package com.example.yum.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val supabase: SupabaseClient
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var visiblePassword by mutableStateOf(false)
    var visibleConfirmPassword by mutableStateOf(false)

    fun login(email: String, password: String) {
        Log.d("Login debug", "Login with e-mail ($email)")
        try {
            viewModelScope.launch {
                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
            }
        } catch (e: Exception) {
            Log.e(
                "LoginError",
                "Error during supabase login with email and password",
                e
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            supabase.auth.signOut()
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            try {
                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
            } catch (e: Exception) {
                Log.e(
                    "Sign up error",
                    "Error while signing up $email",
                    e
                )
            }
        }
    }

    fun getUserOrNull() : UserInfo? {
        return supabase.auth.currentUserOrNull()
    }
}
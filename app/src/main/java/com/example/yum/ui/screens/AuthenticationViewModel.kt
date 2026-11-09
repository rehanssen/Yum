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
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.exception.AuthErrorCode

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val supabase: SupabaseClient
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var visiblePassword by mutableStateOf(false)
    var visibleConfirmPassword by mutableStateOf(false)

    var authStateMessage : String? by mutableStateOf(null)

    fun login(email: String, password: String) {
        Log.d("Login debug", "Login with e-mail ($email)")
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
            } catch (e: AuthRestException) {
                when (e.errorCode) {
                    AuthErrorCode.InvalidCredentials -> {
                        Log.e(
                            "LoginError",
                            "Invalid credentials",
                            e
                        )
                        authStateMessage = "Ugyldig innlogging"
                    }
                    else -> {
                        Log.e(
                            "LoginError",
                            "Error during supabase login with email and password",
                            e
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginError", "Unknown error threw generic Exception", e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
            supabase.auth.signOut()
            } catch (e: Exception) {
                Log.e(
                    "LogoutError",
                    "Error during supabase logout",
                    e
                )
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            try {
                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
            } catch (e: AuthRestException) {
                Log.e("SignUp Error", "Code: ${e.errorCode}, Message: ${e.message}", e)
                when (e.errorCode) {
                    AuthErrorCode.EmailExists -> {
                        authStateMessage = "E-post finnes allerede"
                        Log.e(
                            "SignUp Error",
                            "Error signing up. E-mail exists",
                            e
                        )
                    }
                    AuthErrorCode.UserAlreadyExists -> {
                        authStateMessage = "Bruker finnes allerede"
                        Log.e(
                            "SignUp Error",
                            "Error signing up. User already exists",
                            e
                        )
                    }
                    else -> {
                        authStateMessage = "Det skjedde en feil"
                        Log.e(
                            "SignUp Error",
                            "Unknown error signing up",
                            e
                        )
                    }
                }
            } catch (e: Exception) {
                authStateMessage = "Det skjedde en feil"
                Log.e("LoginError", "Unknown error threw generic Exception", e)
            }
        }
    }

    fun getUserOrNull(): UserInfo? {
        return supabase.auth.currentUserOrNull()
    }
}
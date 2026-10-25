package com.example.yum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.yum.ui.Screens.CreateRecipeScreen
import com.example.yum.ui.Screens.HomeScreen
import com.example.yum.ui.Screens.LoginScreen
import com.example.yum.ui.Screens.RegisterScreen
import com.example.yum.ui.theme.YumTheme
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://uhdpcvvakrwwxgotjpue.supabase.co",
    supabaseKey = "sb_publishable_mWZNzSXL3anuC8lZUfTnEw_DJHsA1ie"
) {
    install(Auth)
    install(Postgrest)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            YumTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
//    var isLoggedIn by remember { mutableStateOf(supabase.auth.currentUserOrNull() != null) }
    var registerNewUser by remember { mutableStateOf(false) }
    var createRecipe by remember { mutableStateOf(false) }
    val sessionStatus by supabase.auth.sessionStatus.collectAsState()

    when (sessionStatus) {
        SessionStatus.Initializing -> {
            Text("Laster")
        }

        is SessionStatus.Authenticated -> {
            if (createRecipe) {
                CreateRecipeScreen(
                    onSaveRecipe = { createRecipe = false }
                )
            } else {
                HomeScreen(
                    onCreateRecipe = {
                        createRecipe = true
                    }
                )
            }
        }

        is SessionStatus.NotAuthenticated,
        is SessionStatus.RefreshFailure -> {
            if (registerNewUser) {
                RegisterScreen(
                    onUserRegistered = { registerNewUser = false }
                )
            } else {
                LoginScreen(
                    onRegisterNewUser = { registerNewUser = true }
                )
            }
        }
    }
}
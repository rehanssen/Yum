package com.example.yum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.yum.ui.AppNavigation
import com.example.yum.ui.AuthNavigation
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
//    var registerNewUser by remember { mutableStateOf(false) }
//    var createRecipe by remember { mutableStateOf(false) }
    val sessionStatus by supabase.auth.sessionStatus.collectAsState()

    when (sessionStatus) {
        SessionStatus.Initializing -> {
            Text("Laster")
        }

        is SessionStatus.Authenticated -> {
            AppNavigation()
//
//
//            if (createRecipe) {
//                CreateRecipeScreen(
//                    onSaveRecipe = { createRecipe = false }
//                )
//            } else {
//                HomeScreen(
//                    onCreateRecipe = {
//                        createRecipe = true
//                    }
//                )
//            }
        }

        is SessionStatus.NotAuthenticated,
        is SessionStatus.RefreshFailure -> {
            AuthNavigation()
//            if (registerNewUser) {
//                RegisterScreen(
//                    onUserRegistered = { registerNewUser = false }
//                )
//            } else {
//                LoginScreen(
//                    onRegisterNewUser = { registerNewUser = true }
//                )
//            }
        }
    }
}
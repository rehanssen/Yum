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
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var supabase: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            YumTheme {
                App(supabase)
            }
        }
    }
}

@Composable
fun App(supabase: SupabaseClient) {
    val sessionStatus by supabase.auth.sessionStatus.collectAsState()

    when (sessionStatus) {
        SessionStatus.Initializing -> {
            Text("Laster")
        }

        is SessionStatus.Authenticated -> {
            AppNavigation(supabase)
        }

        is SessionStatus.NotAuthenticated,
        is SessionStatus.RefreshFailure -> {
            AuthNavigation(supabase)
        }
    }
}
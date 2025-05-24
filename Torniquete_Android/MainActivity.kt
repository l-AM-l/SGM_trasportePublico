package com.example.apptorniquete

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apptorniquete.ui.theme.AppTorniqueteTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        auth.signOut() // Para que siempre inicie en login

        setContent {
            AppTorniqueteTheme {
                AppNavigation(auth)
            }
        }
    }
}

@Composable
fun AppNavigation(auth: FirebaseAuth) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("qr") { QrPlaceholderScreen(auth, navController)}
        composable("recover") { RecoverPasswordScreen(navController)}
    }
}

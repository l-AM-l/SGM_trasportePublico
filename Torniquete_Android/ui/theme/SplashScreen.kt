package com.example.apptorniquete

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SplashScreen(navController: NavController? = null) {
    LaunchedEffect(Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            navController?.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }, 2000) // 2 segundos
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SplashCircleBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("APP", fontSize = 70.sp, fontWeight = FontWeight.Bold, color = Color(0xFF007AFF))
            Text("Torniquete", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color(0xFF007AFF))
            Spacer(modifier = Modifier.height(16.dp))
            Text("  Hecha por", fontSize = 24.sp, color = Color(0xFF007AFF))
            Text("   Ana y Natalia", fontSize = 26.sp, fontWeight = FontWeight.Medium, color = Color(0xFF007AFF))
        }
    }
}

@Composable
fun SplashCircleBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-60).dp, y = (-60).dp)
                .clip(CircleShape)
                .background(Color(0xFFA3D4FF))
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 280.dp, y = 100.dp)
                .clip(CircleShape)
                .background(Color(0xFFC8E6C9))
        )
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = 150.dp, y = 650.dp)
                .clip(CircleShape)
                .background(Color(0xFFBBDEFB))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}

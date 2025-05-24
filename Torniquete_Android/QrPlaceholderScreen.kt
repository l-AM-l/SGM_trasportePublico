package com.example.apptorniquete

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.google.firebase.auth.FirebaseAuth

@Composable
fun QrPlaceholderScreen(auth: FirebaseAuth, navController: NavController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CircleBackgroundQr()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(context, QrScannerActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Escanear boleto QR", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    auth.signOut()
                    Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("qr") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Cerrar sesión", color = Color.White)
            }
        }
    }
}

@Composable
fun CircleBackgroundQr() {
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
                .size(180.dp)
                .offset(x = 290.dp, y = 150.dp)
                .clip(CircleShape)
                .background(Color(0xFFC8E6C9))
        )
        Box(
            modifier = Modifier
                .size(450.dp)
                .offset(x = 180.dp, y = 650.dp)
                .clip(CircleShape)
                .background(Color(0xFFBBDEFB))
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewQrPlaceholderScreenSafe() {
    val fakeAuth = FirebaseAuth.getInstance()
    val navController = rememberNavController()

    QrPlaceholderScreen(auth = fakeAuth, navController = navController)
}

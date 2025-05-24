package com.example.apptorniquete

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RecoverPasswordScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CircleBackgroundRecover()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Recuperar contraseña",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isNotBlank()) {
                        auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Enlace de recuperación enviado", Toast.LENGTH_LONG).show()
                                    navController.navigate("login") {
                                        popUpTo("recover") { inclusive = true }
                                    }
                                } else {
                                    val mensaje = when (task.exception?.message) {
                                        "The email address is badly formatted." ->
                                            "El correo electrónico no tiene un formato válido."
                                        "There is no user record corresponding to this identifier. The user may have been deleted." ->
                                            "No hay ninguna cuenta con ese correo."
                                        else -> "Error: ${task.exception?.message}"
                                    }
                                    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Por favor ingresa un correo", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Text("Enviar enlace", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("login") }) {
                Text("Volver", color = Color.Gray)
            }
        }
    }
}

@Composable
fun CircleBackgroundRecover() {
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

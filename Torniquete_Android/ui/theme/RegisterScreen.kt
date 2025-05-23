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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun RegisterScreen(navController: NavController? = null) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var semestre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        RegisterCircleBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crear Cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, placeholder = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = apellido, onValueChange = { apellido = it }, placeholder = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = carrera, onValueChange = { carrera = it }, placeholder = { Text("Carrera") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = semestre, onValueChange = { semestre = it }, placeholder = { Text("Semestre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = correo, onValueChange = { correo = it }, placeholder = { Text("Correo electrónico") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = contraseña, onValueChange = { contraseña = it }, placeholder = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = confirmar, onValueChange = { confirmar = it }, placeholder = { Text("Confirmar contraseña") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (contraseña == confirmar) {
                        auth.createUserWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val userId = auth.currentUser?.uid
                                    val ref = database.getReference("usuarios").child(userId ?: "")
                                    ref.setValue(
                                        mapOf(
                                            "nombre" to nombre,
                                            "apellido" to apellido,
                                            "carrera" to carrera,
                                            "semestre" to semestre,
                                            "correo" to correo
                                        )
                                    )
                                    Toast.makeText(context, "Registrado correctamente", Toast.LENGTH_LONG).show()
                                    navController?.navigate("login")
                                } else {
                                    val mensaje = when (task.exception?.message) {
                                        "The email address is badly formatted." ->
                                            "El correo electrónico no tiene un formato válido."
                                        "The given password is invalid. [ Password should be at least 6 characters ]" ->
                                            "La contraseña debe tener al menos 6 caracteres."
                                        "The email address is already in use by another account." ->
                                            "Este correo ya está registrado."
                                        else -> "Error: ${task.exception?.message}"
                                    }
                                    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Registrar", color = Color.White)
            }
        }
    }
}

@Composable
fun RegisterCircleBackground() {
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
                .offset(x = (180).dp, y = 650.dp)
                .clip(CircleShape)
                .background(Color(0xFFBBDEFB))
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewRegisterScreenSafe() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        RegisterCircleBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crear Cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            repeat(7) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Campo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Registrar", color = Color.White)
            }
        }
    }
}

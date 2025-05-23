package com.example.apptorniquete

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator

class QrScannerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Escanea tu boleto")
        integrator.setBeepEnabled(true)
        integrator.setOrientationLocked(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            val clave = result.contents
            val database = FirebaseDatabase.getInstance()
            val clavesRef = database.getReference("claves").child(clave)

            clavesRef.get().addOnSuccessListener { snapshot ->
                val estado = snapshot.getValue(String::class.java)
                if (estado == "QR generado") {
                    clavesRef.setValue("QR utilizado")
                    Toast.makeText(this, "Buen viaje 🚍", Toast.LENGTH_LONG).show()
                } else if (estado == "QR utilizado") {
                    Toast.makeText(this, "QR ya utilizado, genere uno nuevo", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "QR inválido", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
        }
        finish()
        super.onActivityResult(requestCode, resultCode, data)
    }
}

package com.example.aplicacionhealthcare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var etDoctor: EditText = findViewById(R.id.etDoctor)
        var etEspecialidad: EditText = findViewById(R.id.etEspecialidad)
        var etFecha: EditText = findViewById(R.id.etFecha)
        var etHora: EditText = findViewById(R.id.etHora)
        var etMotivo: EditText = findViewById(R.id.etMotivo)
        var btnGuardar: Button = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {

            var doctor: String = etDoctor.text.toString()
            var especialidad: String = etEspecialidad.text.toString()
            var fecha: String = etFecha.text.toString()
            var hora: String = etHora.text.toString()
            var motivo: String = etMotivo.text.toString()

            if (doctor.isEmpty() || especialidad.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Complete los campos obligatorios.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Información guardada correctamente.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
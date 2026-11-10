package com.example.aplicacionhealthcare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etDoctor = findViewById<EditText>(R.id.etDoctor)
        val etEspecialidad = findViewById<EditText>(R.id.etEspecialidad)
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val etHora = findViewById<EditText>(R.id.etHora)
        val etMotivo = findViewById<EditText>(R.id.etMotivo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnBuscarMedicos = findViewById<Button>(R.id.btnBuscarMedicos)

        btnGuardar.setOnClickListener {
            val doctor = etDoctor.text.toString().trim()
            val especialidad = etEspecialidad.text.toString().trim()
            val fecha = etFecha.text.toString().trim()
            val hora = etHora.text.toString().trim()
            val motivo = etMotivo.text.toString().trim()

            if (doctor.isEmpty() || especialidad.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Complete los campos obligatorios.", Toast.LENGTH_LONG).show()
            } else {
                RegistroActivity.citasRegistradas.add(
                    Cita(doctor, especialidad, fecha, hora, motivo)
                )
                Toast.makeText(this, "Información guardada correctamente.", Toast.LENGTH_LONG).show()
                etDoctor.text.clear()
                etEspecialidad.text.clear()
                etFecha.text.clear()
                etHora.text.clear()
                etMotivo.text.clear()
            }
        }

        btnBuscarMedicos.setOnClickListener {
            val intent = Intent(this, TarjetasActivity::class.java)
            startActivity(intent)
        }
    }
}

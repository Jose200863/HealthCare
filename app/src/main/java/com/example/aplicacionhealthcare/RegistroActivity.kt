package com.example.aplicacionhealthcare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class Cita(
    val doctor: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val motivo: String
)

class RegistroActivity : AppCompatActivity() {

    companion object {
        val citasRegistradas = mutableListOf(
            Cita("Dra. María Rodríguez", "Cardiología", "12/11/2026", "09:00 am", "Control de presión"),
            Cita("Dr. Carlos Méndez", "Pediatría", "13/11/2026", "10:30 am", "Control de crecimiento"),
            Cita("Dra. Ana López", "Dermatología", "14/11/2026", "02:00 pm", "Revisión de lunares")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val doctor = findViewById<EditText>(R.id.etDoctor)
        val especialidad = findViewById<EditText>(R.id.etEspecialidad)
        val fecha = findViewById<EditText>(R.id.etFecha)
        val hora = findViewById<EditText>(R.id.etHora)
        val motivo = findViewById<EditText>(R.id.etMotivo)
        val guardar = findViewById<Button>(R.id.btnGuardar)

        doctor.setText(intent.getStringExtra("doctor") ?: "")
        especialidad.setText(intent.getStringExtra("especialidad") ?: "")

        guardar.setOnClickListener {
            val nombreDoctor = doctor.text.toString().trim()
            val nombreEspecialidad = especialidad.text.toString().trim()
            val fechaCita = fecha.text.toString().trim()
            val horaCita = hora.text.toString().trim()
            val motivoCita = motivo.text.toString().trim()

            if (nombreDoctor.isBlank() ||
                nombreEspecialidad.isBlank() ||
                fechaCita.isBlank() ||
                horaCita.isBlank()
            ) {
                Toast.makeText(
                    this,
                    "Complete los campos obligatorios",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            citasRegistradas.add(
                Cita(
                    nombreDoctor,
                    nombreEspecialidad,
                    fechaCita,
                    horaCita,
                    motivoCita
                )
            )

            Toast.makeText(
                this,
                "Cita registrada correctamente",
                Toast.LENGTH_LONG
            ).show()

            fecha.text.clear()
            hora.text.clear()
            motivo.text.clear()
        }
    }
}

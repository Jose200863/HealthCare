package com.example.aplicacionhealthcare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionhealthcare.data.CitasRepository

/**
 * Pantalla de registro de una cita.
 *
 * Puede abrirse desde "Buscar médicos", en cuyo caso recibe el doctor y la
 * especialidad seleccionados. Al guardar, la cita se agrega al repositorio
 * en memoria y se abre la pantalla de búsqueda para verla en la lista.
 */
class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        val etDoctor = findViewById<EditText>(R.id.etDoctor)
        val etEspecialidad = findViewById<EditText>(R.id.etEspecialidad)
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val etHora = findViewById<EditText>(R.id.etHora)
        val etMotivo = findViewById<EditText>(R.id.etMotivo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Datos enviados desde la pantalla de médicos.
        etDoctor.setText(intent.getStringExtra(EXTRA_DOCTOR).orEmpty())
        etEspecialidad.setText(intent.getStringExtra(EXTRA_ESPECIALIDAD).orEmpty())

        btnGuardar.setOnClickListener {
            val doctor = etDoctor.text.toString().trim()
            val especialidad = etEspecialidad.text.toString().trim()
            val fecha = etFecha.text.toString().trim()
            val hora = etHora.text.toString().trim()
            val motivo = etMotivo.text.toString().trim()

            if (doctor.isEmpty() || especialidad.isEmpty() ||
                fecha.isEmpty() || hora.isEmpty()
            ) {
                Toast.makeText(this, "Complete los campos obligatorios.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            CitasRepository.agregar(doctor, especialidad, fecha, hora, motivo)
            Toast.makeText(this, "Cita registrada correctamente.", Toast.LENGTH_LONG).show()

            // Muestra la cita recién registrada en la pantalla de búsqueda.
            startActivity(Intent(this, ActividadBusqueda::class.java))
            finish()
        }
    }

    companion object {
        private const val EXTRA_DOCTOR = "doctor"
        private const val EXTRA_ESPECIALIDAD = "especialidad"

        /** Crea el intent con el médico y la especialidad ya seleccionados. */
        fun crearIntent(context: Context, medico: Medico): Intent =
            Intent(context, RegistroActivity::class.java).apply {
                putExtra(EXTRA_DOCTOR, medico.nombre)
                putExtra(EXTRA_ESPECIALIDAD, medico.especialidad)
            }
    }
}

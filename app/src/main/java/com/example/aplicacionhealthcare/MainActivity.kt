package com.example.aplicacionhealthcare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacionhealthcare.data.CitasRepository

/**
 * Pantalla principal: permite agendar una cita y abrir las demás pantallas.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var etDoctor: EditText
    private lateinit var etEspecialidad: EditText
    private lateinit var etFecha: EditText
    private lateinit var etHora: EditText
    private lateinit var etMotivo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etDoctor = findViewById(R.id.etDoctor)
        etEspecialidad = findViewById(R.id.etEspecialidad)
        etFecha = findViewById(R.id.etFecha)
        etHora = findViewById(R.id.etHora)
        etMotivo = findViewById(R.id.etMotivo)

        aplicarInsets()

        findViewById<Button>(R.id.btnGuardar).setOnClickListener { guardarCita() }
        findViewById<Button>(R.id.btnBuscarCitas).setOnClickListener {
            abrir(ActividadBusqueda::class.java)
        }
        findViewById<Button>(R.id.btnBuscarMedicos).setOnClickListener {
            abrir(TarjetasActivity::class.java)
        }
    }

    /** Guarda la cita si los campos obligatorios están completos. */
    private fun guardarCita() {
        val doctor = etDoctor.text.toString().trim()
        val especialidad = etEspecialidad.text.toString().trim()
        val fecha = etFecha.text.toString().trim()
        val hora = etHora.text.toString().trim()
        val motivo = etMotivo.text.toString().trim()

        if (doctor.isEmpty() || especialidad.isEmpty() ||
            fecha.isEmpty() || hora.isEmpty()
        ) {
            Toast.makeText(this, "Complete los campos obligatorios.", Toast.LENGTH_LONG).show()
            return
        }

        CitasRepository.agregar(doctor, especialidad, fecha, hora, motivo)
        Toast.makeText(this, "Información guardada correctamente.", Toast.LENGTH_LONG).show()
        limpiarCampos()
        abrir(ActividadBusqueda::class.java)
    }

    private fun limpiarCampos() {
        etDoctor.text.clear()
        etEspecialidad.text.clear()
        etFecha.text.clear()
        etHora.text.clear()
        etMotivo.text.clear()
    }

    private fun abrir(destino: Class<*>) {
        startActivity(Intent(this, destino))
    }

    /** Evita que la barra de estado tape el contenido. */
    private fun aplicarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { vista, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            vista.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }
    }
}

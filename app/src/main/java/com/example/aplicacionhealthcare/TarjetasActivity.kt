package com.example.aplicacionhealthcare

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged

/** Médico disponible para agendar una cita. */
data class Medico(
    val nombre: String,
    val especialidad: String,
    val horario: String
)

/**
 * Pantalla "Buscar médicos".
 *
 * Muestra una tarjeta por cada médico disponible y permite filtrarlos por
 * nombre y especialidad. Al tocar una tarjeta se abre el registro de la cita
 * con el doctor y la especialidad ya seleccionados.
 */
class TarjetasActivity : AppCompatActivity() {

    private val todas = "Todas"

    private val medicos = listOf(
        Medico("Dra. María Rodríguez", "Cardiología", "8:00 am - 12:00 pm"),
        Medico("Dr. Carlos Méndez", "Pediatría", "9:00 am - 2:00 pm"),
        Medico("Dra. Ana López", "Dermatología", "10:00 am - 4:00 pm"),
        Medico("Dr. José Vargas", "Cardiología", "1:00 pm - 5:00 pm"),
        Medico("Dra. Laura Jiménez", "Pediatría", "7:00 am - 1:00 pm"),
        Medico("Dr. Andrés Castro", "Dermatología", "11:00 am - 5:00 pm"),
        Medico("Dra. Sofía Mora", "Medicina General", "8:00 am - 3:00 pm"),
        Medico("Dr. Daniel Rojas", "Medicina General", "9:00 am - 6:00 pm")
    )

    private lateinit var etBuscar: EditText
    private lateinit var spCategoria: Spinner
    private lateinit var contenedorTarjetas: LinearLayout
    private lateinit var tvSinResultados: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarjetas)

        etBuscar = findViewById(R.id.etBuscar)
        spCategoria = findViewById(R.id.spCategoria)
        contenedorTarjetas = findViewById(R.id.contenedorTarjetas)
        tvSinResultados = findViewById(R.id.tvSinResultados)

        cargarCategorias()
        configurarEventos()
        filtrar()
    }

    /** Llena el selector con "Todas" y las especialidades disponibles. */
    private fun cargarCategorias() {
        val categorias = listOf(todas) +
            medicos.map { it.especialidad }
                .distinctBy { it.lowercase() }
                .sorted()

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categorias
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategoria.adapter = adaptador
    }

    /** Vuelve a filtrar cuando cambia el texto o la especialidad. */
    private fun configurarEventos() {
        etBuscar.doAfterTextChanged { filtrar() }

        spCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) = filtrar()

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    /** Filtra los médicos y vuelve a dibujar las tarjetas. */
    private fun filtrar() {
        val texto = etBuscar.text.toString().trim()
        val seleccion = spCategoria.selectedItem?.toString() ?: todas

        val encontrados = medicos.filter { medico ->
            val coincideNombre = texto.isEmpty() ||
                medico.nombre.contains(texto, ignoreCase = true)
            val coincideEspecialidad = seleccion == todas ||
                medico.especialidad.equals(seleccion, ignoreCase = true)

            coincideNombre && coincideEspecialidad
        }

        contenedorTarjetas.removeAllViews()
        tvSinResultados.isVisible = encontrados.isEmpty()
        encontrados.forEach { medico ->
            contenedorTarjetas.addView(crearTarjeta(medico))
        }
    }

    /** Crea la tarjeta que representa a un médico. */
    private fun crearTarjeta(medico: Medico): TextView {
        val margen = (16 * resources.displayMetrics.density).toInt()

        val tarjeta = TextView(this).apply {
            text = "${medico.nombre}\n${medico.especialidad}\nHorario: ${medico.horario}"
            textSize = 17f
            setPadding(margen, margen, margen, margen)
            setBackgroundResource(R.drawable.bg_tarjeta_medico)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = margen
            }
            setOnClickListener {
                startActivity(
                    RegistroActivity.crearIntent(this@TarjetasActivity, medico)
                )
            }
        }

        return tarjeta
    }
}

package com.example.aplicacionhealthcare

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.aplicacionhealthcare.data.Cita
import com.example.aplicacionhealthcare.data.CitasRepository

/**
 * Pantalla de búsqueda de citas.
 *
 * Permite buscar por el nombre del doctor y filtrar por especialidad.
 * Los resultados se actualizan cada vez que el usuario escribe o cambia
 * la especialidad seleccionada.
 */
class ActividadBusqueda : AppCompatActivity() {

    private val todasLasEspecialidades = "Todas las especialidades"

    private lateinit var etBuscar: EditText
    private lateinit var spEspecialidad: Spinner
    private lateinit var tvContador: TextView
    private lateinit var tvSinResultados: TextView
    private lateinit var lvResultados: ListView
    private lateinit var btnVolver: Button
    private lateinit var adaptador: ArrayAdapter<Cita>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.actividad_busqueda)

        etBuscar = findViewById(R.id.etBuscar)
        spEspecialidad = findViewById(R.id.spEspecialidad)
        tvContador = findViewById(R.id.tvContador)
        tvSinResultados = findViewById(R.id.tvSinResultados)
        lvResultados = findViewById(R.id.lvResultados)
        btnVolver = findViewById(R.id.btnVolver)

        btnVolver.setOnClickListener { finish() }

        crearAdaptador()
        cargarEspecialidades()
        configurarEventos()
        filtrar()
    }

    /** Crea el adaptador que muestra el doctor y los datos de la cita. */
    private fun crearAdaptador() {
        adaptador = object : ArrayAdapter<Cita>(this, android.R.layout.simple_list_item_2) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val vista = convertView
                    ?: layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false)
                val cita = getItem(position)!!

                vista.findViewById<TextView>(android.R.id.text1).text = cita.doctor
                vista.findViewById<TextView>(android.R.id.text2).text =
                    "${cita.especialidad} · ${cita.fecha} · ${cita.hora}"

                return vista
            }
        }
        lvResultados.adapter = adaptador
    }

    /** Llena el selector con todas las especialidades disponibles. */
    private fun cargarEspecialidades() {
        val especialidades = listOf(todasLasEspecialidades) +
            CitasRepository.obtenerEspecialidades()

        val adaptadorSpinner = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            especialidades
        )
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEspecialidad.adapter = adaptadorSpinner
    }

    /** Vuelve a filtrar cuando cambia el texto o la especialidad. */
    private fun configurarEventos() {
        etBuscar.doAfterTextChanged { filtrar() }

        spEspecialidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) = filtrar()

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    /** Aplica el texto y la especialidad, y actualiza la lista de resultados. */
    private fun filtrar() {
        val nombre = etBuscar.text.toString()
        val seleccion = spEspecialidad.selectedItem?.toString()
        val especialidad = seleccion.takeUnless { it == todasLasEspecialidades }

        val resultados = CitasRepository.filtrar(nombre, especialidad)

        adaptador.clear()
        adaptador.addAll(resultados)

        val hayResultados = resultados.isNotEmpty()
        tvContador.isVisible = hayResultados
        lvResultados.isVisible = hayResultados
        tvSinResultados.isVisible = !hayResultados

        if (hayResultados) {
            tvContador.text = if (resultados.size == 1) {
                "1 cita encontrada"
            } else {
                "${resultados.size} citas encontradas"
            }
        }
    }
}

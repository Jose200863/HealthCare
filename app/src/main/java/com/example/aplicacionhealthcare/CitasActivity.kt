package com.example.aplicacionhealthcare

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CitasActivity : AppCompatActivity() {

    private lateinit var buscar: EditText
    private lateinit var categoria: Spinner
    private lateinit var resultados: LinearLayout
    private lateinit var mensaje: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        buscar = findViewById(R.id.etBuscar)
        categoria = findViewById(R.id.spCategoria)
        resultados = findViewById(R.id.contenedorTarjetas)
        mensaje = findViewById(R.id.tvSinResultados)

        val categorias = listOf(
            "Todas",
            "Cardiología",
            "Pediatría",
            "Dermatología",
            "Medicina General"
        )

        categoria.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )

        categoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                filtrar()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        buscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int,
                count: Int, after: Int
            ) = Unit

            override fun onTextChanged(
                s: CharSequence?, start: Int,
                before: Int, count: Int
            ) {
                filtrar()
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        filtrar()
    }

    override fun onResume() {
        super.onResume()
        filtrar()
    }

    private fun filtrar() {
        val texto = buscar.text.toString().trim()
        val seleccion = categoria.selectedItem?.toString() ?: "Todas"

        val filtrados = RegistroActivity.citasRegistradas.filter { cita ->
            val coincideTexto = cita.doctor.contains(texto, ignoreCase = true) ||
                    cita.motivo.contains(texto, ignoreCase = true)
            val coincideEspecialidad = seleccion == "Todas" ||
                    cita.especialidad.equals(seleccion, ignoreCase = true)

            coincideTexto && coincideEspecialidad
        }

        resultados.removeAllViews()

        mensaje.visibility = if (filtrados.isEmpty()) View.VISIBLE else View.GONE

        filtrados.forEach { cita ->
            val tarjeta = TextView(this).apply {
                text = "Médico: ${cita.doctor}\n" +
                        "Especialidad: ${cita.especialidad}\n" +
                        "Fecha: ${cita.fecha}  |  Hora: ${cita.hora}\n" +
                        "Motivo: ${cita.motivo}"

                textSize = 16f
                setPadding(30, 30, 30, 30)
                setBackgroundResource(R.drawable.bg_tarjeta_medico)

                val parametros = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                parametros.bottomMargin = 16
                layoutParams = parametros
            }

            resultados.addView(tarjeta)
        }
    }
}

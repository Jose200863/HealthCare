package com.example.aplicacionhealthcare

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

data class Medico(
    val nombre: String,
    val especialidad: String,
    val horario: String
)

class TarjetasActivity : AppCompatActivity() {

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

    private lateinit var buscar: EditText
    private lateinit var categoria: Spinner
    private lateinit var resultados: LinearLayout
    private lateinit var mensaje: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarjetas)

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

        categoria.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filtrar()
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) = Unit
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

    private fun filtrar() {
        val texto = buscar.text.toString().trim()
        val seleccion = categoria.selectedItem?.toString() ?: "Todas"

        val filtrados = medicos.filter {
            it.nombre.contains(texto, ignoreCase = true) &&
                    (seleccion == "Todas" ||
                            it.especialidad == seleccion)
        }

        resultados.removeAllViews()

        mensaje.visibility =
            if (filtrados.isEmpty()) View.VISIBLE else View.GONE

        filtrados.forEach { medico ->
            val tarjeta = TextView(this).apply {
                text = "${medico.nombre}\n" +
                        "${medico.especialidad}\n" +
                        "Horario: ${medico.horario}"

                textSize = 17f
                setPadding(30, 30, 30, 30)
                setBackgroundResource(
                    R.drawable.bg_tarjeta_medico
                )

                val parametros = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                parametros.bottomMargin = 16
                layoutParams = parametros
            }

            tarjeta.setOnClickListener {
                val intent = Intent(
                    this,
                    RegistroActivity::class.java
                )
                intent.putExtra("doctor", medico.nombre)
                intent.putExtra("especialidad", medico.especialidad)
                startActivity(intent)
            }

            resultados.addView(tarjeta)
        }
    }
}

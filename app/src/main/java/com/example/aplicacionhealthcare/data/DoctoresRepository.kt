package com.example.aplicacionhealthcare.data

import com.example.aplicacionhealthcare.models.Doctor
import java.text.Normalizer

object DoctoresRepository {

    const val TODAS = "Todas las especialidades"

    private val doctores = listOf(

        Doctor(
            nombre = "Dr. Juan Pérez",
            especialidad = "Cardiología",
            modalidad = "Presencial",
            experiencia = 5
        ),

        Doctor(
            nombre = "Dra. Laura Martínez",
            especialidad = "Cardiología",
            modalidad = "VideoConsulta",
            experiencia = 4
        ),

        Doctor(
            nombre = "Dra. Ana Gómez",
            especialidad = "Dermatología",
            modalidad = "Presencial",
            experiencia = 6
        ),

        Doctor(
            nombre = "Dr. Carlos Rodríguez",
            especialidad = "Dermatología",
            modalidad = "VideoConsulta",
            experiencia = 5
        ),

        Doctor(
            nombre = "Dr. Luis Rodríguez",
            especialidad = "Medicina General",
            modalidad = "Presencial",
            experiencia = 8
        ),

        Doctor(
            nombre = "Dra. María López",
            especialidad = "Medicina General",
            modalidad = "Presencial",
            experiencia = 7
        ),

        Doctor(
            nombre = "Dr. Carlos Sánchez",
            especialidad = "Pediatría",
            modalidad = "Presencial",
            experiencia = 3
        ),

        Doctor(
            nombre = "Sofia Hernandez",
            especialidad = "Audiología",
            modalidad = "Presencial",
            experiencia = 6
        ),

        Doctor(
            nombre = "Dra. Laura García",
            especialidad = "Oftalmología",
            modalidad = "Presencial",
            experiencia = 4
        )
    )

    // Devuelve todos los doctores

    fun obtenerTodos(): List<Doctor> = doctores

    // Devuelve "Todas" + las especialidades sin repetir (para el Spinner)

    fun obtenerEspecialidades(): List<String> {
        return listOf(TODAS) + doctores.map { it.especialidad }.distinct()
    }

    // Combina búsqueda por nombre + filtro por categoría
    fun buscar(texto: String, categoria: String): List<Doctor> {
        val textoNormalizado = normalizar(texto.trim())

        return doctores.filter { doctor ->
            val coincideNombre = normalizar(doctor.nombre).contains(textoNormalizado)
            val coincideCategoria = categoria == TODAS || doctor.especialidad == categoria
            coincideNombre && coincideCategoria
        }
    }

    // Minúsculas y sin tildes: "maria" encuentra "María"
    private fun normalizar(texto: String): String {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace("\\p{M}+".toRegex(), "")
            .lowercase()
    }
}
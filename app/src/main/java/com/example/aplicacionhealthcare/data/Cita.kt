package com.example.aplicacionhealthcare.data

/**
 * Representa una cita médica agendada en la aplicación.
 *
 * @param id Identificador único de la cita.
 * @param doctor Nombre del doctor o doctora.
 * @param especialidad Categoría a la que pertenece la cita.
 * @param fecha Fecha de la cita en formato dd/mm/aaaa.
 * @param hora Hora de la cita.
 * @param motivo Motivo de la consulta.
 */
data class Cita(
    val id: Int,
    val doctor: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val motivo: String
)

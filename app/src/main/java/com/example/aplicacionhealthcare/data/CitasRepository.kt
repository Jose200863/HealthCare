package com.example.aplicacionhealthcare.data

/**
 * Repositorio en memoria de las citas médicas.
 *
 * Los datos viven únicamente durante la ejecución de la aplicación, por lo que
 * no se utiliza base de datos, archivos ni servicios externos.
 */
object CitasRepository {

    private val citas = mutableListOf(
        Cita(1, "Dra. Ana Torres", "Cardiología", "12/11/2026", "09:00 a. m.", "Control de presión arterial"),
        Cita(2, "Dr. Luis Ramírez", "Cardiología", "14/11/2026", "10:30 a. m.", "Revisión de electrocardiograma"),
        Cita(3, "Dra. Sofía Méndez", "Pediatría", "13/11/2026", "08:30 a. m.", "Control de crecimiento"),
        Cita(4, "Dr. Carlos Vega", "Pediatría", "15/11/2026", "11:00 a. m.", "Vacunación infantil"),
        Cita(5, "Dra. Valentina Rojas", "Dermatología", "16/11/2026", "09:30 a. m.", "Revisión de lunares"),
        Cita(6, "Dr. Andrés Salazar", "Dermatología", "18/11/2026", "02:00 p. m.", "Tratamiento de acné"),
        Cita(7, "Dra. Camila Ortega", "Medicina General", "17/11/2026", "10:00 a. m.", "Chequeo general"),
        Cita(8, "Dr. Daniel Castro", "Medicina General", "19/11/2026", "04:30 p. m.", "Control de exámenes"),
        Cita(9, "Dra. Isabel Núñez", "Neurología", "20/11/2026", "11:30 a. m.", "Evaluación de migrañas"),
        Cita(10, "Dr. Marco Herrera", "Neurología", "21/11/2026", "03:00 p. m.", "Seguimiento de tratamiento")
    )

    private var siguienteId = citas.size + 1

    /** Registra una nueva cita y la coloca al inicio de la lista. */
    fun agregar(
        doctor: String,
        especialidad: String,
        fecha: String,
        hora: String,
        motivo: String
    ) {
        citas.add(0, Cita(siguienteId++, doctor, especialidad, fecha, hora, motivo))
    }

    /** Obtiene las especialidades disponibles sin repetir y en orden alfabético. */
    fun obtenerEspecialidades(): List<String> =
        citas.map { it.especialidad }
            .distinctBy { it.lowercase() }
            .sorted()

    /**
     * Filtra las citas por el nombre del doctor y por la especialidad.
     *
     * @param nombre Texto completo o parcial. Se ignoran mayúsculas y minúsculas.
     * @param especialidad Especialidad seleccionada, o null para mostrar todas.
     * @return Lista de citas que cumplen ambos criterios.
     */
    fun filtrar(nombre: String, especialidad: String?): List<Cita> {
        val texto = nombre.trim()
        return citas.filter { cita ->
            val coincideNombre = texto.isEmpty() ||
                cita.doctor.contains(texto, ignoreCase = true)
            val coincideEspecialidad = especialidad == null ||
                cita.especialidad.equals(especialidad, ignoreCase = true)
            coincideNombre && coincideEspecialidad
        }
    }
}

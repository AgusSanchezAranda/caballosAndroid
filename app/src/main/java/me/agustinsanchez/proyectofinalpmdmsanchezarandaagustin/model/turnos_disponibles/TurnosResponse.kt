package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.turnos_disponibles

data class TurnosResponse(
    val success: Boolean,
    val data: List<String>,
    val message: String
)

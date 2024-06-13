package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.reservas

data class ReservasResponse(
    val success: Boolean,
    val data: List<Reserva>,
    val message: String
)

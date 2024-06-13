package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.reservas

data class Reserva(
    val id: Int,
    val nombre_caballo: String,
    val fecha_reserva: String,
    val turno: String,
    val comentario: String
)
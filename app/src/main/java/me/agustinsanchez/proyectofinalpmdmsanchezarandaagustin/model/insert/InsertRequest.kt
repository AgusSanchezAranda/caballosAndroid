package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.insert

data class InsertRequest (
    val caballo: String,
    val fecha_reserva: String,
    val turno: Int,
    val comentario: String
)

package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.insert

data class InsertData (
    val id: Int,
    val id_user: Int,
    val id_caballo: Int,
    val fecha_reserva: String,
    val turno: String,
    val comentario: String
)
package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.caballos_disponibles

data class CaballosResponse(
    val success: Boolean,
    val data: List<String>,
    val message: String
)

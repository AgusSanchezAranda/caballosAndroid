package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.register

data class RegisterRequest (
    val name: String,
    val email: String,
    val password: String,
    val confirm_password: String
)
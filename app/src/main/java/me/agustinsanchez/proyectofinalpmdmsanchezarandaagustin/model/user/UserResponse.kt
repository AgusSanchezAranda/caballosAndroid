package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.user

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val email_verified_at: String?,
    val two_factor_confirmed_at: String?,
    val current_team_id: Int?,
    val profile_photo_path: String?,
    val created_at: String,
    val updated_at: String,
    val profile_photo_url: String?
)
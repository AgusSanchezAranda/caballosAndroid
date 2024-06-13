package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.repository

import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.login.LoginRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.login.TokenResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.logout.LogoutResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.register.RegisterRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.user.UserResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.network.ApiAdapter
import retrofit2.Response

/**
 * Repositorio que maneja las llamadas a la Api relacionadas con la autenticación
 */
class AuthRepository {

    //hago uso del patrón Singleton
    private val apiService = ApiAdapter.instance

    suspend fun login(email: String, password: String): Response<TokenResponse>? {
        val request = LoginRequest(email, password)
        return apiService?.login(request)
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Response<TokenResponse>? {
        val request = RegisterRequest(name, email, password, confirmPassword)
        return apiService?.register(request)
    }

    suspend fun getInfoUser(token: String): Response<UserResponse>? {
        return apiService?.getInfoUser("Bearer $token")
    }

    suspend fun logOutUser(token: String): Response<LogoutResponse>? {
        return apiService?.logOutUser("Bearer $token")
    }
}
package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.network

import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.caballos_disponibles.CaballoRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.caballos_disponibles.CaballosResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.delete.DeleteResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.insert.InsertRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.insert.InsertResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.login.LoginRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.register.RegisterRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.login.TokenResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.logout.LogoutResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.reservas.ReservasResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.turnos_disponibles.TurnoRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.turnos_disponibles.TurnosResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz que define el método por el cual accedemos a la API
 */

interface ApiService {

    @POST("login")
    suspend fun login (
        @Body request: LoginRequest
    ): Response<TokenResponse>

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<TokenResponse>

    @GET("reservas/reservas-rw")
    suspend fun getReservas(
        @Header("Authorization") token: String
    ): Response<ReservasResponse>

    @DELETE("reservas/{id}")
    suspend fun deleteReserva(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<DeleteResponse>

    @GET("user")
    suspend fun getInfoUser(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @POST("reservas/turnos-disponibles")
    suspend fun getTurnos(
        @Header("Authorization") token: String,
        @Body request: TurnoRequest
    ): Response<TurnosResponse>

    @POST("reservas/caballos-disponibles")
    suspend fun getCaballos(
        @Header("Authorization") token: String,
        @Body request: CaballoRequest
    ): Response<CaballosResponse>

    @POST("reservas")
    suspend fun createReserva(
        @Header("Authorization") token: String,
        @Body request: InsertRequest
    ): Response<InsertResponse>

    @PUT("reservas/{id}")
    suspend fun updateReserva(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("caballo") caballo: String,
        @Query("fecha_reserva") fechaReserva: String,
        @Query("turno") turno: Int,
        @Query("comentario") comentario: String
    ): Response<InsertResponse>

    @POST("logout")
    suspend fun logOutUser(
        @Header("Authorization") token: String,
    ): Response<LogoutResponse>
}
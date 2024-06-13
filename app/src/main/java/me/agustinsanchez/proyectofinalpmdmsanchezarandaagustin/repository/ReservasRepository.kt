package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.repository


import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.caballos_disponibles.CaballoRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.caballos_disponibles.CaballosResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.delete.DeleteResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.insert.InsertRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.insert.InsertResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.reservas.ReservasResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.turnos_disponibles.TurnoRequest
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.model.turnos_disponibles.TurnosResponse
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.network.ApiAdapter
import retrofit2.Response

/**
 * Repositorio que maneja el tratamiento de las reservas
 */

class ReservasRepository {

    private val apiService = ApiAdapter.instance

    suspend fun getReservas(token: String): Response<ReservasResponse>? {
        return apiService?.getReservas("Bearer $token")
    }

    suspend fun deleteReserva(token: String, id: Int): Response<DeleteResponse>? {
        return apiService?.deleteReserva("Bearer $token", id)
    }

    suspend fun getTurnos(token: String, fecha: String): Response<TurnosResponse>?{
        val request = TurnoRequest(fecha)
        return apiService?.getTurnos("Bearer $token", request)
    }

    suspend fun getCaballos(token: String, fecha: String, turno: Int): Response<CaballosResponse>?{
        val request = CaballoRequest(fecha, turno)
        return apiService?.getCaballos("Bearer $token", request)
    }

    suspend fun createReserva(token: String, caballo: String, fecha: String, turno: Int, comentario: String): Response<InsertResponse>?{
        val request = InsertRequest(caballo, fecha, turno, comentario)
        return apiService?.createReserva("Bearer $token", request)
    }

    suspend fun updateReserva(token: String, id: Int, caballo: String, fecha: String, turno: Int, comentario: String): Response<InsertResponse>?{
        return apiService?.updateReserva("Bearer $token", id, caballo, fecha, turno, comentario)
    }

}
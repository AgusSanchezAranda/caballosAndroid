package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * patrón Singleton
 * Garantiza que una clase tenga sólo una instancia y es un punto de acceso
 * global a esa instancia.
 */
object ApiAdapter {
    private var API_SERVICE: ApiService? = null
    val BASE_URL = "https://caballos.agustinsanchez.me/api/"

    @get:Synchronized
    val instance: ApiService?
        get() {
            if (API_SERVICE == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                API_SERVICE = retrofit.create(ApiService::class.java)
            }
            return API_SERVICE
        }
}
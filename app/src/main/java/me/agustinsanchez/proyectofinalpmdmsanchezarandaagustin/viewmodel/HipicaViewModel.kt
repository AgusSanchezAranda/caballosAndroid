package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.adapter.ReservaModel
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.repository.AuthRepository
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.repository.ReservasRepository

class HipicaViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    private val reservasRepository = ReservasRepository()
    var token: String? = null
    var name: String? = null
    var email: String? = null
    var reservaSeleccionada: ReservaModel? = null
    var turnosDisponibles: List<String>? = null
    var caballosDisponibles: List<String>? = null


    //lista observada
    private val _reservas = MutableLiveData<List<ReservaModel>>()
    val reservas: LiveData<List<ReservaModel>> get() = _reservas


    suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val response = authRepository.login(email, password)
            if (response?.isSuccessful == true) {
                val tokenResponse = response.body()
                token = tokenResponse?.data?.token
                name = tokenResponse?.data?.name
                if(tokenResponse?.success!!) {
                    true
                }else{
                    false
                }
            } else {
                false
            }
        }
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            val response = authRepository.register(name, email, password, confirmPassword)
            if (response?.isSuccessful == true) {
                true
            } else {
                false
            }
        }
    }

    suspend fun getReservas() {
        token?.let { authToken ->
            val response = withContext(Dispatchers.IO) {
                reservasRepository.getReservas(authToken)
            }

            //mapeo de la respuesta a la lista del RW
            if (response?.isSuccessful == true) {
                val reservasResponse = response.body()
                val reservasModel = reservasResponse?.data?.map {
                    ReservaModel(
                        id = it.id,
                        caballo = it.nombre_caballo,
                        fechaReserva = it.fecha_reserva,
                        turno = it.turno,
                        comentario = it.comentario
                    )
                } ?: emptyList()  //si la respuesta no contiene datos. lista vacía

                //Actualiza el liveData
                _reservas.postValue(reservasModel)
            }
        }
    }

    suspend fun deleteReserva(id: Int) {
        token?.let { authToken ->
            val response = withContext(Dispatchers.IO) {
                reservasRepository.deleteReserva(authToken, id)
                getReservas() //actualiza la lista
            }
        }
    }

    suspend fun getInfoUser() {
        token?.let { authToken ->
            val response = withContext(Dispatchers.IO) {
                authRepository.getInfoUser(authToken)
            }
            if (response?.isSuccessful == true) {
                val userResponse = response.body()
                email = userResponse?.email
            }
        }
    }


    suspend fun getTurnos(fecha: String) {
        token?.let { authToken ->
            val response = withContext(Dispatchers.IO) {
                reservasRepository.getTurnos(authToken, fecha)
            }
            if (response?.isSuccessful == true) {
                val turnosResponse = response.body()
                turnosDisponibles = turnosResponse?.data

            }
        }
    }

    suspend fun getCaballos(fecha: String, turno: Int) {
        token?.let { authToken ->
            val response = withContext(Dispatchers.IO){
                reservasRepository.getCaballos(authToken, fecha, turno)
            }
            if(response?.isSuccessful == true){
                val caballosResponse = response.body()
                caballosDisponibles = caballosResponse?.data
            }
        }
    }

    suspend fun createReserva(caballo: String, fecha: String, turno: Int, comentario: String){
        token?.let {authToken ->
            val response = withContext(Dispatchers.IO){
                reservasRepository.createReserva(authToken, caballo, fecha, turno, comentario)
                getReservas() //actualiza la lista
            }
        }
    }

    suspend fun updateReserva(caballo: String, fecha: String, turno: Int, comentario: String) {
        var id = reservaSeleccionada?.id
        token?.let { authToken ->
            val response = withContext(Dispatchers.IO) {
                reservasRepository.updateReserva(authToken, id!!, caballo, fecha, turno, comentario)
                getReservas() //actualiza la lista
            }
        }
    }

    suspend fun logOutUser(){
        token?.let { authToken ->
            val response = withContext(Dispatchers.IO){
                authRepository.logOutUser(authToken)
            }
        }
    }

}//class



package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.databinding.ItemReservaBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HipicaRwAdapter(private var reservasList: List<ReservaModel>):
    RecyclerView.Adapter<HipicaRwAdapter.HipicaRwViewHolder>() {

        fun updateList(newList: List<ReservaModel>){
            val reservaDiff = HipicaRwDiffUtil(reservasList, newList)
            val result = DiffUtil.calculateDiff(reservaDiff)
            reservasList = newList
            result.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HipicaRwViewHolder {
        val itemBinding = ItemReservaBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return HipicaRwViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = reservasList.size

    override fun onBindViewHolder(holder: HipicaRwViewHolder, position: Int) {
        val item = reservasList[position]
        holder.render(item)
    }


    inner class HipicaRwViewHolder(binding: ItemReservaBinding) : RecyclerView.ViewHolder(binding.root){
        private val nomCaballo = binding.tvCaballo
        private val fecha = binding.tvFecha
        private val turno = binding.tvTurno
        private val comentario = binding.tvComentario
        private val logo = binding.ivLogo

        fun render(reserva: ReservaModel){

            val nombreCaballo = reserva.caballo

            nomCaballo.setText(nombreCaballo)
            fecha.setText(reserva.fechaReserva)
            turno.setText(reserva.turno)
            comentario.setText(reserva.comentario)

            Glide.with(logo.context)
                .load("https://agustinsanchez.me/caballos/$nombreCaballo.png")
                .into(logo)

        }
    }



}//class
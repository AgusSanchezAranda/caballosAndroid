package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.databinding.FragmentInfoReservaBinding
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.viewmodel.HipicaViewModel


class InfoReservaFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentInfoReservaBinding
    private val hipicaViewModel: HipicaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoReservaBinding.inflate(inflater, container, false)
        val view = binding.root

        //recuperamos datos del viewModel
        var nombreJinete = hipicaViewModel.name
        var email = hipicaViewModel.email
        var fecha = hipicaViewModel.reservaSeleccionada?.fechaReserva
        var turno = hipicaViewModel.reservaSeleccionada?.turno
        var nombreCaballo = hipicaViewModel.reservaSeleccionada?.caballo
        var comentario = hipicaViewModel.reservaSeleccionada?.comentario

        binding.tvNombreJinete.setText(nombreJinete)
        binding.TVemail.setText(email)
        binding.TVFecha.setText(fecha)
        binding.TVTurno.setText(turno)
        binding.TVCaballo.setText(nombreCaballo)
        binding.TVComentario.setText(comentario)


        binding.btnVolver.setOnClickListener(this)


        return view
    }

    override fun onClick(v: View?) {
        if(v === binding.btnVolver){
            findNavController().navigate(InfoReservaFragmentDirections.actionInfoReservaFragmentPop())
        }
    }


}
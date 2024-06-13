package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.databinding.FragmentInicioBinding

class InicioFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentInicioBinding
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btlogin.setOnClickListener(this)
        binding.btregister.setOnClickListener(this)

        // Crea un callback para manejar el evento de retroceso
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Maneja la lógica de retroceso aquí
              requireActivity().finishAffinity()
            }
        }
        // Registra el callback en el dispatcher de retroceso de la actividad
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return view
    }

    override fun onClick(v: View?) {

        //acciones botón Login
        if(v === binding.btlogin){
            findNavController().navigate(InicioFragmentDirections.actionInicioFragmentToLoginFragment())
        }

        //acciones botón Registro
        if(v === binding.btregister){
            findNavController().navigate(InicioFragmentDirections.actionInicioFragmentToRegisterFragment())
        }
    }

}
package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.views

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.databinding.FragmentLoginBinding
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.viewmodel.HipicaViewModel


class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val hipicaViewModel: HipicaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btlogin.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        //acciones botón login
        if(v === binding.btlogin){
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(email.isBlank() || password.isBlank()){
                Toast.makeText(requireContext(), "Algún campo está vacío", Toast.LENGTH_SHORT).show()
            } else {
                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String){
        lifecycleScope.launch {
            val success = hipicaViewModel.login(email, password)
            if(success){
                var token = hipicaViewModel.token
                Log.e("TRAZA", "$success  $token")
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToReservasFragment())

            } else {
                //login fallido
                Toast.makeText(requireContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                binding.etEmail.setText("")
                binding.etPassword.setText("")
            }
        }



    }


}
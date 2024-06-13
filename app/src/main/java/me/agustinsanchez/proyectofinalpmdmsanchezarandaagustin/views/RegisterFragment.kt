package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.databinding.FragmentRegisterBinding
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.viewmodel.HipicaViewModel


class RegisterFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentRegisterBinding
    private val hipicaViewModel: HipicaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btregister.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        //acciones del botón Registro
        if(v === binding.btregister){
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            if(name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
                Toast.makeText(requireContext(), "Algún campo está vacío", Toast.LENGTH_SHORT).show()
            } else {
                register(name, email, password, confirmPassword)
            }
        }
    }

    private fun register(name: String, email: String, password: String, confirmPassword: String){
        lifecycleScope.launch {
            val success = hipicaViewModel.register(name, email, password, confirmPassword)
            if(success){
                //registro exitoso, navegamos a la pantalla de login
                Toast.makeText(requireContext(), "Se ha registrado con éxito. Vaya a su correo y confirmelo", Toast.LENGTH_SHORT).show()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            } else {
                //login fallido
                Toast.makeText(requireContext(), "Fallo en el registro. Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                binding.etName.setText("")
                binding.etEmail.setText("")
                binding.etPassword.setText("")
                binding.etConfirmPassword.setText("")
            }
        }



    }


}
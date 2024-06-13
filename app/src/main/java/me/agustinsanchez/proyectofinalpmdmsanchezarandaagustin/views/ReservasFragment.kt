package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.R
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.adapter.HipicaRwAdapter
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.adapter.RecyclerTouchListener
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.adapter.ReservaModel
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.adapter.SwipeToDeleteCallback
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.databinding.FragmentReservasBinding
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.viewmodel.HipicaViewModel


class ReservasFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentReservasBinding
    private lateinit var callback: OnBackPressedCallback
    private val hipicaViewModel: HipicaViewModel by activityViewModels()
    private lateinit var adapter: HipicaRwAdapter
    private var listaReservasRw: List<ReservaModel> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservasBinding.inflate(inflater, container, false)
        val view = binding.root

        //toolbar
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        //Rescato toda la info del user
        lifecycleScope.launch {
            hipicaViewModel.getInfoUser()
        }

        initRecyclerView()
        getReservas()

        //Observa el liveData de reservas
        hipicaViewModel.reservas.observe(viewLifecycleOwner, { reservas ->
            listaReservasRw = reservas
            //usa diffutils para actualizar el RW
            adapter.updateList(reservas)
        })


        //listeners
        binding.BTAgregarReserva.setOnClickListener(this)

        // Crea un callback para manejar el evento de retroceso
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Maneja la lógica de retroceso aquí
                Toast.makeText(requireContext(), "Para salir, pulse Log Out", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // Registra el callback en el dispatcher de retroceso de la actividad
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return view
    }

    private fun initRecyclerView() {
        adapter = HipicaRwAdapter(listaReservasRw)

        binding.rwReservas.layoutManager = LinearLayoutManager(requireContext())
        binding.rwReservas.adapter = adapter

        //listener de clicks en el rw
        binding.rwReservas.addOnItemTouchListener(
            RecyclerTouchListener(
                requireContext(),
                binding.rwReservas,
                object : RecyclerTouchListener.ClickListener {
                    //toque corto
                    override fun onClick(view: View?, position: Int) {
                        //con esta consulta en el fragment de edición tendré disponible en el VM la reserva seleccionada.
                        hipicaViewModel.reservaSeleccionada = listaReservasRw[position]
                        findNavController().navigate(ReservasFragmentDirections.actionReservasFragmentToInfoReservaFragment())
                    }

                    //toque largo
                    override fun onLongClick(view: View?, position: Int) {
                        //con esta consulta en el fragment de edición tendré disponible en el VM la reserva seleccionada.
                        hipicaViewModel.reservaSeleccionada = listaReservasRw[position]
                        findNavController().navigate(ReservasFragmentDirections.actionReservasFragmentToEditReservaFragment())
                    }
                })
        )

        //configuracion del ItemTouchHelper para el deslizamiento
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(object :
            SwipeToDeleteCallback.OnItemDeleteListener {
            override fun onDeleteItem(position: Int) {
                //establecemos que se está realizando un deslizamiento
                var isSwiping = true
                val reservaEliminar = listaReservasRw[position]
                val dialog = AlertDialog.Builder(requireContext())
                    .setPositiveButton("Si, eliminar") { dialog, which ->
                        lifecycleScope.launch {
                            hipicaViewModel.deleteReserva(reservaEliminar.id)
                        }
                    }
                    .setNegativeButton("Cancelar") { dialog, which ->
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                    .setTitle("Confirmar")
                    .setMessage("¿Eliminar Reserva?")
                    .create()
                dialog.show()
                isSwiping = false
            }
        }))
        itemTouchHelper.attachToRecyclerView(binding.rwReservas)

    }//initRecyclerView


    private fun getReservas() {
        lifecycleScope.launch {
            hipicaViewModel.getReservas()

        }
    }

    override fun onClick(v: View?) {
        if (v === binding.BTAgregarReserva) {
            findNavController().navigate(ReservasFragmentDirections.actionReservasFragmentToAddReservaFragment())
        }
    }

    //Gestión del toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
    }

    //Eleccion de opciones del toolBar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }

            R.id.acercaDe -> {
                acercaDeDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun logout(){
        hipicaViewModel.token = null
        hipicaViewModel.name = null
        hipicaViewModel.email = null

        //hago logout al sistema con la api
        lifecycleScope.launch {
            hipicaViewModel.logOutUser()
        }
        findNavController().navigate(ReservasFragmentDirections.actionReservasFragmentToInicioFragment())
    }


    fun acercaDeDialog() {
        val mensaje = "Agustín Sánchez Aranda\nProyecto Final PMDM\nCurso 23/24\n2º D.A.M. Semipresencial"
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Acerca De")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
}
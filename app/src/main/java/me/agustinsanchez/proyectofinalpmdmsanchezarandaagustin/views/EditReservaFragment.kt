package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.views

//noinspection SuspiciousImport
import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.databinding.FragmentEditReservaBinding
import me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.viewmodel.HipicaViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditReservaFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentEditReservaBinding
    private val hipicaViewModel: HipicaViewModel by activityViewModels()
    lateinit var datePickerDialog: DatePickerDialog
    lateinit var adapterTurnos: ArrayAdapter<String>
    lateinit var adapterCaballos: ArrayAdapter<String>

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private var fechaString: String = ""
    private var turno: Int = 10
    private var caballo: String = ""
    private var comentario: String = ""
    private var telefono: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditReservaBinding.inflate(inflater,container,false)
        val view = binding.root

        //rescato la fecha y el comentario de la reserva
        fechaString = hipicaViewModel.reservaSeleccionada?.fechaReserva ?: "API problem"
        comentario = hipicaViewModel.reservaSeleccionada?.comentario ?: "API problem"
        binding.TvFechaPaseo.setText(fechaString)
        binding.etComentario.setText(comentario)

        // Inicialización de los adaptadores vacíos
        adapterTurnos =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, emptyList())
        adapterTurnos.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spTurno.adapter = adapterTurnos
        binding.spTurno.isEnabled = false

        adapterCaballos =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, emptyList())
        adapterCaballos.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spCaballos.adapter = adapterCaballos
        binding.spCaballos.isEnabled = false

        //configuración del DatePicker
        val calendario = Calendar.getInstance()
        val fecha = DatePickerDialog.OnDateSetListener { datepicker, year, month, day ->
            calendario.set(Calendar.YEAR, year)
            calendario.set(Calendar.MONTH, month)
            calendario.set(Calendar.DAY_OF_MONTH, day)

            val dayOfWeek = calendario.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                fechaString = dateFormat.format(calendario.time)
                binding.TvFechaPaseo.setText(fechaString)
                lifecycleScope.launch {
                    hipicaViewModel.getTurnos(fechaString)
                    adapterTurnos = ArrayAdapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        hipicaViewModel.turnosDisponibles ?: emptyList()
                    )
                    adapterTurnos.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding.spTurno.isEnabled = true
                    binding.spTurno.adapter = adapterTurnos
                }
            } else {
                showToast("El día elegido no es fin de semana. No hay paseo esa fecha!")
            }
        }
        datePickerDialog = DatePickerDialog(
            requireContext(),
            fecha,
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()

        calendario.add(Calendar.DAY_OF_YEAR, 30)
        val maxDate = calendario.timeInMillis
        datePickerDialog.datePicker.maxDate = maxDate

        //listener que gestiona la selección del spinner de turno y activa los caballos
        binding.spTurno.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedTurno = parent?.getItemAtPosition(position).toString()
                when (selectedTurno) {
                    "Turno 10:00" -> {
                        turno = 10
                    }

                    "Turno 11:00" -> {
                        turno = 11
                    }

                    "Turno 12:00" -> {
                        turno = 12
                    }

                    "Turno 13:00" -> {
                        turno = 13
                    }
                }
                lifecycleScope.launch {
                    hipicaViewModel.getCaballos(fechaString, turno)

                    adapterCaballos = ArrayAdapter(
                        requireContext(),
                        R.layout.simple_spinner_item,
                        hipicaViewModel.caballosDisponibles ?: emptyList()
                    )
                    adapterCaballos.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding.spCaballos.isEnabled = true
                    binding.spCaballos.adapter = adapterCaballos
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada sino se selecciona nada
            }
        }

        //listeners
        binding.btnGuardar.setOnClickListener(this)
        binding.btnCancelar.setOnClickListener(this)
        binding.BtFechaPaseo.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        if (v === binding.BtFechaPaseo) {
            //desactivar y poner vacios los spinners
            adapterCaballos =
                ArrayAdapter(requireContext(), R.layout.simple_spinner_item, emptyList())
            adapterCaballos.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            adapterTurnos =
                ArrayAdapter(requireContext(), R.layout.simple_spinner_item, emptyList())
            adapterTurnos.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            binding.spTurno.isEnabled = false
            binding.spCaballos.isEnabled = false

            datePickerDialog.show()
        }

        if (v === binding.btnGuardar) {
            caballo = binding.spCaballos.selectedItem as String
            comentario = binding.etComentario.getText().toString()
            telefono = binding.etTelefono.getText().toString()
            if (!telefono.equals("")) {
                checkTelefono(telefono)
            }

            if (comentario.isBlank()) {
                comentario = "Sin Comentarios"
            }

            if (fechaString.isBlank()) {
                showToast("No ha seleccionado una fecha")
            } else {
                lifecycleScope.launch {
                    hipicaViewModel.updateReserva(caballo, fechaString, turno, comentario)

                    showToast("Reserva actualizada correctamente")

                    // enviar wassap
                    if (binding.cbwssp.isChecked && !telefono.equals("")) {
                        val message =
                            "Datos de su actualización de reserva\nfecha--> $fechaString\nturno--> $turno\ncaballo--> $caballo"
                        enviarWhtApp(message, telefono)
                    }

                    findNavController().navigate(EditReservaFragmentDirections.actionEditReservaFragmentPop())
                }
            }
        }

        if (v === binding.btnCancelar) {
            findNavController().navigate(EditReservaFragmentDirections.actionEditReservaFragmentPop())
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }//showToast

    private fun checkTelefono(phone: String) {
        val regex = Regex("^[6-7]\\d{8}\$")
        if (!regex.matches(phone)) {
            showToast("No ha introducido un Teléfono móvil correcto!")
            binding.etTelefono.setText("")
            telefono = ""
            binding.cbwssp.isChecked = false
        }
    }

    fun enviarWhtApp(message: String, telefono: String) {
        val mensaje = message
        val phoneNumber = telefono
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data =
            Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(mensaje)}")
        startActivity(intent)
    }

}
package com.dsa.practicekotlin2.ui.horoscope

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dsa.practicekotlin2.R
import com.dsa.practicekotlin2.databinding.FragmentHoroscopeBinding
import com.dsa.practicekotlin2.domain.model.HoroscopeInfo
import com.dsa.practicekotlin2.domain.model.HoroscopeInfo.*
import com.dsa.practicekotlin2.domain.model.HoroscopeModel
import com.dsa.practicekotlin2.ui.horoscope.adapter.HoroscopeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopeFragment : Fragment() {

    private val horoscopeViewModel by viewModels<HoroscopeViewModel>()
    private lateinit var horoscopeAdapter: HoroscopeAdapter

    private var _binding: FragmentHoroscopeBinding? = null
    private val binding get() = _binding!!

    // Se llama cuando la vista del fragmento ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initList()
        initUIState()
    }

    // Inicializa la lista de horóscopos y configura el RecyclerView
    private fun initList() {
        // Crea un adaptador para la lista de horóscopos
        horoscopeAdapter = HoroscopeAdapter(onItemSelected =  {
            // Convierte el tipo de horóscopo seleccionado a un tipo específico del modelo
            val type = when(it) {
                Aquarius -> HoroscopeModel.Aquarius
                Aries -> HoroscopeModel.Aries
                Cancer -> HoroscopeModel.Cancer
                Capricorn -> HoroscopeModel.Capricorn
                Gemini -> HoroscopeModel.Gemini
                Leo -> HoroscopeModel.Leo
                Libra -> HoroscopeModel.Libra
                Pisces -> HoroscopeModel.Pisces
                Sagittarius -> HoroscopeModel.Sagittarius
                Scorpio -> HoroscopeModel.Scorpio
                Taurus -> HoroscopeModel.Taurus
                Virgo -> HoroscopeModel.Virgo
            }

            // Navega a otra actividad pasando el tipo de horóscopo como argumento
            findNavController().navigate(
                HoroscopeFragmentDirections.actionHoroscopeFragmentToHoroscopeDetailActivity2(type)
            )
        })

        // Configura el RecyclerView con un GridLayoutManager y el adaptador
        binding.rvHoroscope.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = horoscopeAdapter
        }
    }

    // Inicializa el estado de la interfaz de usuario mediante la observación de un flujo de datos
    private fun initUIState() {
        lifecycleScope.launch {
            // Repite la recolección del flujo de datos mientras el fragmento esté en estado STARTED
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Recolecta el flujo de datos del ViewModel y actualiza la lista del adaptador
                horoscopeViewModel.horoscope.collect{
                    horoscopeAdapter.updateList(it)
                }
            }
        }
    }

    // Se llama cuando la vista del fragmento está siendo creada
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoroscopeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    // Se llama cuando el fragmento está siendo destruido
    override fun onDestroy() {
        super.onDestroy()
        // Establece el View Binding en null para evitar pérdidas de memoria
        _binding = null
    }
    
}
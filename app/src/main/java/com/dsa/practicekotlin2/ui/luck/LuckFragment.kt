package com.dsa.practicekotlin2.ui.luck

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.dsa.practicekotlin2.R
import com.dsa.practicekotlin2.databinding.FragmentLuckBinding
import com.dsa.practicekotlin2.ui.core.listeners.OnSwipeTouchListener
import com.dsa.practicekotlin2.ui.providers.RandomCardProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import java.util.Random

@AndroidEntryPoint
class LuckFragment : Fragment() {

    private var _binding: FragmentLuckBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var randomCardProvider: RandomCardProvider

    // Se ejecuta cuando la vista del fragmento ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa la interfaz de usuario
        initUI()
    }

    // Inicializa la interfaz de usuario y otras configuraciones
    private fun initUI() {
        // Prepara y muestra la predicción en la interfaz de usuario
        preparePrediction()

        // Inicializa los escuchadores de eventos
        initListeners()
    }

    // Obtiene una predicción aleatoria y actualiza la interfaz de usuario
    private fun preparePrediction() {
        val currentLuck = randomCardProvider.getLucky()
        currentLuck?.let { luck ->
            // Obtiene la predicción y muestra la información en la interfaz de usuario
            val currentPrediction = getString(luck.text)
            binding.tvLucky.text = currentPrediction
            binding.ivLuckyCard.setImageResource(luck.image)

            // Configura el botón de compartir para compartir la predicción
            binding.tvShare.setOnClickListener{ shareResult(currentPrediction) }
        }
    }

    // Prepara e inicia un Intent para compartir la predicción
    private fun shareResult(prediction: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, prediction)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    // Inicializa los escuchadores de eventos, como el deslizamiento en la imagen de la ruleta
    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        // Configura el deslizamiento en la imagen de la ruleta para activar la rotación
        binding.ivRoulette.setOnTouchListener(object: OnSwipeTouchListener(requireContext()) {
            override fun onSwipeRight() {
                spinRoulette()
            }

            override fun onSwipeLeft() {
                spinRoulette()
            }
        })
    }

    // Realiza la animación de rotación de la ruleta
    private fun spinRoulette() {
        val random = Random()
        val degrees = random.nextInt(1440) + 360

        val animator = ObjectAnimator.ofFloat(binding.ivRoulette, View.ROTATION, 0F, degrees.toFloat())
        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        animator.doOnEnd { slideCard() }
        animator.start()
    }

    // Realiza la animación de deslizamiento hacia arriba para la tarjeta
    private fun slideCard() {
        val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        slideUpAnimation.setAnimationListener(object: Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
                binding.reverse.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                growCard()
            }

            override fun onAnimationRepeat(animation: Animation?) { }

        })
        binding.reverse.startAnimation(slideUpAnimation)
    }

    // Realiza la animación de crecimiento para la tarjeta
    private fun growCard() {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)

        growAnimation.setAnimationListener(object: Animation.AnimationListener{

            override fun onAnimationStart(animation: Animation?) { }

            override fun onAnimationEnd(animation: Animation?) {
                binding.reverse.isVisible = true
                showPremonitionView()
            }

            override fun onAnimationRepeat(animation: Animation?) { }

        })

        binding.reverse.startAnimation(growAnimation)
    }

    // Realiza animaciones de desaparición y aparición para las vistas de premonición y predicción
    private fun showPremonitionView() {
        val disapperAnimation = AlphaAnimation(1.0f, 0.0f)
        disapperAnimation.duration = 200

        val appearAnimation = AlphaAnimation(0.0f, 1.0f)
        appearAnimation.duration = 1000

        disapperAnimation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) { }

            override fun onAnimationEnd(animation: Animation?) {
                binding.preview.isVisible = false
                binding.prediction.isVisible = true
            }

            override fun onAnimationRepeat(animation: Animation?) { }

        })

        binding.preview.startAnimation(disapperAnimation)
        binding.prediction.startAnimation(appearAnimation)
    }

    // Crea y devuelve la vista del fragmento inflando el diseño mediante FragmentLuckBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLuckBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    // Se llama cuando el fragmento está a punto de ser destruido, se utiliza para limpiar referencias y liberar recursos
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}









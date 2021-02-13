package io.haikova.amiilibo.presentation.amiibo

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.FragmentAmiiboDetailsBinding


class AmiiboDetailsFragment : Fragment() {

  private var _binding: FragmentAmiiboDetailsBinding? = null
  private val binding get() = _binding!!

  private val viewModel: AmiiboDetailsViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentAmiiboDetailsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.amiibo_thumb)

    Palette.Builder(bitmap).generate {
      it?.let { palette ->
        val colorTo = palette.getLightVibrantColor(Color.WHITE)
        val colorFrom = Color.WHITE
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.apply {
          duration = 250
          addUpdateListener { animator ->
            binding.root.setBackgroundColor(animator.animatedValue as Int)
          }
          start()
        }
      }
    }
  }
}
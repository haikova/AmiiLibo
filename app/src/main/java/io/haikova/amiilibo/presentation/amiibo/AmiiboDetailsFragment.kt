package io.haikova.amiilibo.presentation.amiibo

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.RequestListener
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.FragmentAmiiboDetailsBinding
import javax.inject.Inject

@AndroidEntryPoint
class AmiiboDetailsFragment : Fragment() {

  private var _binding: FragmentAmiiboDetailsBinding? = null
  private val binding get() = _binding!!

  private val glide by lazy { Glide.with(this) }

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
    viewModel.loadData(arguments?.getString(ITEM_ID) ?: "0")

    viewModel.amiiboData.observe(viewLifecycleOwner) { amiibo ->
      with(binding) {
        glide
          .load(amiibo.image)
          .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
              e: GlideException?,
              model: Any?,
              target: Target<Drawable>?,
              isFirstResource: Boolean
            ): Boolean {
              return false
            }

            override fun onResourceReady(
              resource: Drawable?,
              model: Any?,
              target: Target<Drawable>?,
              dataSource: DataSource?,
              isFirstResource: Boolean
            ): Boolean {
              resource?.toBitmap()?.let { bitmap ->
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
              return false
            }
          })
          .into(amiiboImage)

        nameData.text = amiibo.name
        amiiboSeriesData.text = amiibo.amiiboSeries
        characterData.text = amiibo.character
        gameSeriesData.text = amiibo.gameSeries
        jpData.text = getString(R.string.jp_data, amiibo.releaseCountryMap["jp"] ?: "?")
        euData.text = getString(R.string.eu_data, amiibo.releaseCountryMap["eu"] ?: "?")
        naData.text = getString(R.string.na_data, amiibo.releaseCountryMap["na"] ?: "?")
        auData.text = getString(R.string.au_data, amiibo.releaseCountryMap["au"] ?: "?")
      }
    }


  }

  companion object {
    const val ITEM_ID = "item_amiibo_id"
  }
}
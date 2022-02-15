package io.haikova.amiilibo.presentation.amiibo

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.ActivityAmiiboDetailsBinding


@AndroidEntryPoint
class AmiiboDetailsActivity : AppCompatActivity() {

  private var _binding: ActivityAmiiboDetailsBinding? = null
  private val binding get() = _binding!!

  private val glide by lazy { Glide.with(this) }

  private val viewModel: AmiiboDetailsViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityAmiiboDetailsBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.toolbar.setOnClickListener {
      onBackPressed()
    }
    viewModel.amiiboData.observe(this) { amiibo ->
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
                    val colorFrom = palette.getVibrantColor(Color.WHITE)
                    val colorTo = palette.getMutedColor(Color.WHITE)

                    gradientView.background = GradientDrawable(
                      GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(colorFrom, colorTo)
                    )
                    gradientView.animate().alpha(1f).start()
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
        ownButton.text = if (amiibo.isOwned) "Remove from Collection" else "Add to Collection"
        favButton.setImageResource(if (amiibo.isFavourite) R.drawable.ic_favourite_added else R.drawable.ic_favourite)
        ownButton.setOnClickListener {
          viewModel.changeOwnedState()
        }
        favButton.setOnClickListener {
          viewModel.changeFavouriteState()
        }
      }
    }
  }


  companion object {
    const val ITEM_ID = "item_amiibo_id"
  }
}
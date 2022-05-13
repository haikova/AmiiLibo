package io.haikova.amiilibo.presentation.amiibo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.ActivityAmiiboDetailsBinding
import io.haikova.amiilibo.presentation.amiibo.di.AmiiboDetailsScreen
import io.haikova.amiilibo.theme.AppTheme


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
    binding.amiiboImage.setContent {
      AppTheme {
        AmiiboDetailsScreen()
      }
    }
    setContentView(view)

    binding.toolbar.setOnClickListener {
      onBackPressed()
    }
    viewModel.amiiboData.observe(this) { amiibo ->
      with(binding) {

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
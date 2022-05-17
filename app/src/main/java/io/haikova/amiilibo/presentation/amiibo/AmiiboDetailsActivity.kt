package io.haikova.amiilibo.presentation.amiibo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.databinding.ActivityAmiiboDetailsBinding
import io.haikova.amiilibo.presentation.amiibo.di.AmiiboDetailsScreen
import io.haikova.amiilibo.theme.AppTheme


@AndroidEntryPoint
class AmiiboDetailsActivity : AppCompatActivity() {

  private var _binding: ActivityAmiiboDetailsBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityAmiiboDetailsBinding.inflate(layoutInflater)
    val view = binding.root
    binding.composeView.setContent {
      AppTheme {
        AmiiboDetailsScreen()
      }
    }
    setContentView(view)
  }


  companion object {
    const val ITEM_ID = "item_amiibo_id"
  }
}
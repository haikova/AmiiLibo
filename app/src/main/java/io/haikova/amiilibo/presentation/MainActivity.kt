package io.haikova.amiilibo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.ActivityMainBinding
import io.haikova.amiilibo.presentation.home.HomeFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    navController =
      (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController
    binding.bottomNavigation.setupWithNavController(navController)
  }
}
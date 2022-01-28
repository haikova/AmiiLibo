package io.haikova.amiilibo.presentation

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
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
import io.haikova.amiilibo.hideKeyboard
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

  override fun dispatchTouchEvent(event: MotionEvent): Boolean {
    if (event.action == MotionEvent.ACTION_DOWN) {
      val currentFocusedView = currentFocus
      if (currentFocusedView is EditText) {
        val outRect = Rect()
        currentFocusedView.getGlobalVisibleRect(outRect)
        if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
          currentFocusedView.clearFocus()
          currentFocusedView.hideKeyboard()
        }
      }
    }
    return super.dispatchTouchEvent(event)
  }
}
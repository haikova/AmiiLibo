package io.haikova.amiilibo.presentation.amiibo

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


class AmiiboImageBehavior @JvmOverloads constructor(
  context: Context? = null,
  attrs: AttributeSet? = null
) : CoordinatorLayout.Behavior<ImageView>() {

  override fun layoutDependsOn(
    parent: CoordinatorLayout,
    child: ImageView,
    dependency: View
  ): Boolean {
    return dependency is AppBarLayout
  }

  @RequiresApi(Build.VERSION_CODES.R)
  override fun onDependentViewChanged(
    parent: CoordinatorLayout,
    child: ImageView,
    dependency: View
  ): Boolean {

    (dependency as? AppBarLayout)?.let {
      val factor = -it.y / it.totalScrollRange
      child.pivotY = 0.5f
      child.scaleX = (1 - factor / 3)
      child.scaleY = (1 - factor / 3)

      // TODO fix it. move to utils
      val metrics = DisplayMetrics()
      (child.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(metrics)
      val screenWidth = metrics.widthPixels
      val rightX = screenWidth - child.width
      val leftX = (screenWidth - child.width) / 2
      child.x = leftX + (rightX - leftX) * factor
      child.x = child.x + 1
    }
    return super.onDependentViewChanged(parent, child, dependency)
  }
}
package io.haikova.amiilibo.presentation.options

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.FragmentOptionsBinding
import io.haikova.amiilibo.presentation.home.adapter.MainAdapterDelegates
import io.haikova.amiilibo.presentation.options.adapter.FilterAdapterDelegates
import android.widget.FrameLayout

import com.google.android.material.bottomsheet.BottomSheetDialog

import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener

import androidx.annotation.NonNull
import io.haikova.amiilibo.presentation.options.adapter.OptionItem
import io.haikova.amiilibo.toPx


@AndroidEntryPoint
class OptionsDialogFragment : BottomSheetDialogFragment() {
  lateinit var action: (AmiiboOptionsType, String) -> Unit
  private var _binding: FragmentOptionsBinding? = null
  private val binding get() = _binding!!

  private val optionsViewModel: OptionsViewModel by activityViewModels()

  private val adapter by lazy {
    ListDelegationAdapter(
      FilterAdapterDelegates.titleDelegate(),
      FilterAdapterDelegates.optionsListDelegate { optionsViewModel.optionClicked(it) }
    )
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentOptionsBinding.inflate(inflater, container, false)

    dialog?.setOnShowListener { dialog ->
      val d = dialog as BottomSheetDialog
      val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
      val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
      bottomSheetBehavior.isFitToContents = false
      bottomSheetBehavior.expandedOffset = 200.toPx()
      bottomSheetBehavior.addBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
          Log.d("meow", "meow $newState")
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }

      })
      bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.recyclerView.adapter = adapter

    optionsViewModel.data.observe(viewLifecycleOwner, { data ->
      adapter.items = data
      adapter.notifyDataSetChanged()
    })
  }


  companion object {
    const val OPTIONS_TYPE = "Amiibo options type"
  }
}
package io.haikova.amiilibo.presentation.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.FragmentOptionsBinding
import io.haikova.amiilibo.presentation.options.adapter.FilterAdapterDelegates
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.toPx


@AndroidEntryPoint
class OptionsDialogFragment : BottomSheetDialogFragment() {
  private var _binding: FragmentOptionsBinding? = null
  private val binding get() = _binding!!

  private val optionsViewModel: OptionsViewModel by activityViewModels()

  private val filtersAdapter by lazy {
    AsyncListDifferDelegationAdapter(
      FilterAdapterDelegates.differCallback,
      AdapterDelegatesManager<List<ListItem>>()
        .addDelegate(FilterAdapterDelegates.titleDelegate())
        .addDelegate(FilterAdapterDelegates.optionsListDelegate { optionsViewModel.optionClicked(it) })
    )
  }

  override fun getTheme() = R.style.BottomSheetDialogTheme

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
      bottomSheetBehavior.peekHeight = 375.toPx()
      bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    with(binding) {
      recyclerView.apply {
        itemAnimator = null
        adapter = filtersAdapter
      }
      closeButton.setOnClickListener {
        dialog?.dismiss()
      }
    }

    optionsViewModel.data.observe(viewLifecycleOwner) { data ->
      filtersAdapter.items = data
    }
  }
}
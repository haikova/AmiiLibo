package io.haikova.amiilibo.presentation.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.data.OptionModel
import io.haikova.amiilibo.data.db.AmiiboListType
import io.haikova.amiilibo.databinding.FragmentHomeBinding
import io.haikova.amiilibo.presentation.amiibo.AmiiboDetailsActivity
import io.haikova.amiilibo.presentation.home.adapter.MainAdapterDelegates
import io.haikova.amiilibo.presentation.options.OptionsViewModel
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.res.ResourcesCompat
import io.haikova.amiilibo.R
import io.haikova.amiilibo.data.AmiiboOptionsType
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.home.adapter.AmiiboLoadingItem
import io.haikova.amiilibo.presentation.options.OptionsDialogFragment


@AndroidEntryPoint
class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

  private val glide by lazy { Glide.with(this) }
  private val amiiboAdapter by lazy {
    ListDelegationAdapter(
      MainAdapterDelegates.amiiboDelegate(glide) {
        openDetailsScreen(it.id)
      },
      MainAdapterDelegates.amiiboLoadingDelegate()
    )
  }

  private val viewModel: HomeViewModel by viewModels()
  private val optionsViewModel: OptionsViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    with(binding) {
      recyclerViewAmiibo.adapter = amiiboAdapter
      val loadingItemsList = mutableListOf<ListItem>()
      for (i in 0..14) loadingItemsList.add(AmiiboLoadingItem)
      amiiboAdapter.items = loadingItemsList

      filterButton.setOnClickListener { openOptionsDialog() }
    }


    viewModel.amiiboData.observe(viewLifecycleOwner, { data ->
      amiiboAdapter.items = data
      amiiboAdapter.notifyDataSetChanged()
    })

    viewModel.amiiboOptions.observe(viewLifecycleOwner) {
      viewModel.getAmiiboByOptions(it)
    }

    optionsViewModel.selected.observe(viewLifecycleOwner) { data ->
      updateOptionChips(data)
      viewModel.getAmiiboByOptions(
        AmiiboOptionsData(
          amiiboSeries = data.filter { it.type == AmiiboOptionsType.AMIIBO_SERIES }.map { it.name }
        )
      )
    }
  }

  private fun updateOptionChips(data: Set<OptionModel>) {
    with(binding.selectedOptionsChipGroup) {
      removeAllViews()
      data.forEach {
        addView(Chip(activity).apply {
          text = it.name
          isChecked = true
          isCheckable = false
          closeIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, null)
          closeIconTint = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(Color.parseColor("#FFFFFF"))
          )
          isCloseIconVisible = true
          setOnCloseIconClickListener { }
        })
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun openOptionsDialog() {
    OptionsDialogFragment().apply {
      show(this@HomeFragment.childFragmentManager, "tag")
    }
  }

  fun openDetailsScreen(itemId: String) {
    val bundle = bundleOf(AmiiboDetailsActivity.ITEM_ID to itemId)
    findNavController().navigate(R.id.action_homeFragment_to_amiiboDetailsActivity, bundle)
  }
}
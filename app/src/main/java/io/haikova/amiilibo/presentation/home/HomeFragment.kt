package io.haikova.amiilibo.presentation.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.data.AmiiboOptionsType
import io.haikova.amiilibo.data.OptionModel
import io.haikova.amiilibo.databinding.FragmentHomeBinding
import io.haikova.amiilibo.getColorFromAttr
import io.haikova.amiilibo.presentation.amiibo.AmiiboDetailsActivity
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.home.adapter.AmiiboLoadingItem
import io.haikova.amiilibo.presentation.home.adapter.MainAdapterDelegates
import io.haikova.amiilibo.presentation.options.OptionsDialogFragment
import io.haikova.amiilibo.presentation.options.OptionsViewModel


@AndroidEntryPoint
class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

  private val glide by lazy { Glide.with(this) }
  private val amiiboAdapter by lazy {
    AsyncListDifferDelegationAdapter(
      MainAdapterDelegates.differCallback,
      AdapterDelegatesManager<List<ListItem>>()
        .addDelegate(MainAdapterDelegates.amiiboDelegate(glide) {
          openDetailsScreen(it.id)
        })
        .addDelegate(MainAdapterDelegates.amiiboLoadingDelegate())
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

      searchTextField.editText?.setText(viewModel.searchData.value)
      searchTextField.editText?.setOnFocusChangeListener { view, focused ->
        viewModel.changeState(focused, (view as? EditText)?.text?.toString() ?: "")
      }
      searchTextField.editText?.doOnTextChanged { text, start, before, count ->
        viewModel.setSearchData(text.toString())
        viewModel.changeState(binding.searchTextField.editText?.isFocused ?: false, binding.searchTextField.editText?.text.toString())
      }
    }

    viewModel.screenState.observe(viewLifecycleOwner) { state ->
      binding.filterButton.isVisible = state.isFilterButtonVisible
      binding.searchImageView.isVisible = state.isSearchImageVisible
      binding.searchTextView.isVisible = state.isSearchTextVisible
      binding.recyclerViewAmiibo.isVisible = state.isListVisible
    }


    viewModel.amiiboData.observe(viewLifecycleOwner) { data ->
      amiiboAdapter.items = data
    }

    optionsViewModel.selected.observe(viewLifecycleOwner) { data ->
      updateOptionChips(data)
      viewModel.getAmiiboByOptions(
        AmiiboOptionsData(
          amiiboSeries = data.filter { it.type == AmiiboOptionsType.AMIIBO_SERIES }.map { it.name },
          gameSeries = data.filter { it.type == AmiiboOptionsType.GAME_SERIES }.map { it.name },
          amiiboType = data.filter { it.type == AmiiboOptionsType.AMIIBO_TYPE }.map { it.name },
          character = data.filter { it.type == AmiiboOptionsType.CHARACTER }.map { it.name }
        )
      )
    }
  }

  private fun updateOptionChips(data: Set<OptionModel>) {
    with(binding.selectedOptionsChipGroup) {
      removeAllViews()
      if (data.isEmpty()) {
        binding.filterButton.apply {
          imageTintList = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
              intArrayOf(context.getColorFromAttr(R.attr.Secondary))
          )
          supportBackgroundTintList = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(context.getColorFromAttr(R.attr.Contrast2))
          )
        }
      } else {
        binding.filterButton.apply {
          imageTintList = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(context.getColorFromAttr(R.attr.Primary))
          )
          supportBackgroundTintList = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(context.getColorFromAttr(R.attr.Primary))
          )
        }
      }
      data.forEach { item ->
        addView(Chip(activity).apply {
          text = item.name
          isChecked = true
          isCheckable = false
          closeIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, null)
          closeIconTint = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(resources.getColor(R.color.white))
          )
          isCloseIconVisible = true
          setOnCloseIconClickListener {
            optionsViewModel.removeOption(item)
          }
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

  private fun openDetailsScreen(itemId: String) {
    val bundle = bundleOf(AmiiboDetailsActivity.ITEM_ID to itemId)
    findNavController().navigate(R.id.action_homeFragment_to_amiiboDetailsActivity, bundle)
  }
}
package io.haikova.amiilibo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.data.db.AmiiboListType
import io.haikova.amiilibo.databinding.FragmentHomeBinding
import io.haikova.amiilibo.presentation.amiibo.AmiiboDetailsActivity
import io.haikova.amiilibo.presentation.home.adapter.MainAdapterDelegates
import io.haikova.amiilibo.presentation.options.AmiiboOptionsType
import io.haikova.amiilibo.presentation.options.OptionsDialogFragment
import io.haikova.amiilibo.presentation.options.OptionsDialogFragment.Companion.OPTIONS_TYPE


@AndroidEntryPoint
class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

  private val glide by lazy { Glide.with(this) }
  private val amiiboAdapter by lazy {
    ListDelegationAdapter(
      MainAdapterDelegates.amiiboDelegate(glide) {
        openDetailsScreen(it.id)
      }
    )
  }

  private val viewModel: HomeViewModel by viewModels()

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

      chipSeries.setOnClickListener { openOptionsDialog(AmiiboOptionsType.AMIIBO_SERIES) }
      chipGameSeries.setOnClickListener { openOptionsDialog(AmiiboOptionsType.GAME_SERIES) }
      chipType.setOnClickListener { openOptionsDialog(AmiiboOptionsType.AMIIBO_TYPE) }
      chipCharacter.setOnClickListener { openOptionsDialog(AmiiboOptionsType.CHARACTER) }

      chipSeries.setOnCloseIconClickListener {
        chipSeries.apply {
          text = "Series"
          isCloseIconVisible = false
          viewModel.updateAmiiboOptions(AmiiboOptionsType.AMIIBO_SERIES, null)
        }
      }
      chipGameSeries.setOnCloseIconClickListener {
        chipGameSeries.apply {
          text = "Game series"
          isCloseIconVisible = false
          viewModel.updateAmiiboOptions(AmiiboOptionsType.GAME_SERIES, null)
        }
      }
      chipType.setOnCloseIconClickListener {
        chipType.apply {
          text = "Type"
          isCloseIconVisible = false
          viewModel.updateAmiiboOptions(AmiiboOptionsType.AMIIBO_TYPE, null)
        }
      }
      chipCharacter.setOnCloseIconClickListener {
        chipCharacter.apply {
          text = "Character"
          isCloseIconVisible = false
          viewModel.updateAmiiboOptions(AmiiboOptionsType.CHARACTER, null)
        }
      }
    }


    viewModel.amiiboData.observe(viewLifecycleOwner, { data ->
      amiiboAdapter.items = data
      amiiboAdapter.notifyDataSetChanged()
    })

    viewModel.isProgressShow.observe(viewLifecycleOwner) { show ->
      when (show) {
        true -> {
          binding.progressBar.visibility = View.VISIBLE
          binding.chipGroup.children.forEach { it.isEnabled = false }
        }
        false -> {
          binding.progressBar.visibility = View.INVISIBLE
          binding.chipGroup.children.forEach { it.isEnabled = true }
        }
      }
    }
    viewModel.amiiboOptions.observe(viewLifecycleOwner) {
      viewModel.getAmiiboByOptions(it)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun openOptionsDialog(type: AmiiboOptionsType) {
    OptionsDialogFragment().apply {
      arguments = Bundle().apply {
        putString(OPTIONS_TYPE, type.toString())
      }
      action = { type, title ->
        when (type) {
          AmiiboOptionsType.AMIIBO_SERIES -> binding.chipSeries.apply {
            text = title
            isCloseIconVisible = true
          }
          AmiiboOptionsType.GAME_SERIES -> binding.chipGameSeries.apply {
            text = title
            isCloseIconVisible = true
          }
          AmiiboOptionsType.AMIIBO_TYPE -> binding.chipType.apply {
            text = title
            isCloseIconVisible = true
          }
          AmiiboOptionsType.CHARACTER -> binding.chipCharacter.apply {
            text = title
            isCloseIconVisible = true
          }
        }
        viewModel.updateAmiiboOptions(type, title)
      }

      show(this@HomeFragment.childFragmentManager, "tag")
    }
  }

  fun openDetailsScreen(itemId: String) {
    val bundle = bundleOf(AmiiboDetailsActivity.ITEM_ID to itemId)
    findNavController().navigate(R.id.action_homeFragment_to_amiiboDetailsActivity, bundle)
  }
}
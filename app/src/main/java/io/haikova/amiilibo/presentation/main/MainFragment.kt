package io.haikova.amiilibo.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.databinding.FragmentMainBinding
import io.haikova.amiilibo.databinding.FragmentOptionsBinding
import io.haikova.amiilibo.presentation.options.AmiiboOptionsType
import io.haikova.amiilibo.presentation.options.OptionsDialogFragment
import io.haikova.amiilibo.presentation.options.OptionsDialogFragment.Companion.OPTIONS_TYPE
import io.haikova.amiilibo.presentation.options.OptionsViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {

  private var _binding: FragmentMainBinding? = null
  private val binding get() = _binding!!

  private val glide by lazy { Glide.with(this) }
  private val amiiboAdapter by lazy {
    ListDelegationAdapter(
      MainAdapterDelegates.amiiboDelegate(glide) { }
    )
  }

  private val viewModel: MainViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMainBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    with(binding) {
      recyclerViewAmiibo.adapter = amiiboAdapter

      chipList.setOnClickListener { openOptionsDialog(AmiiboOptionsType.LIST) }
      chipSeries.setOnClickListener { openOptionsDialog(AmiiboOptionsType.AMIIBO_SERIES) }
      chipGameSeries.setOnClickListener { openOptionsDialog(AmiiboOptionsType.GAME_SERIES) }
      chipType.setOnClickListener { openOptionsDialog(AmiiboOptionsType.AMIIBO_TYPE) }
      chipCharacter.setOnClickListener { openOptionsDialog(AmiiboOptionsType.CHARACTER) }
    }


    viewModel.amiiboData.observe(viewLifecycleOwner, { data ->
      amiiboAdapter.items = data
      amiiboAdapter.notifyDataSetChanged()
    })

    viewModel.isProgressShow.observe(viewLifecycleOwner) { show ->
      when(show) {
        true -> binding.progressBar.visibility = View.VISIBLE
        false -> binding.progressBar.visibility = View.INVISIBLE
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
          AmiiboOptionsType.LIST -> binding.chipList.text = title
          AmiiboOptionsType.AMIIBO_SERIES -> binding.chipSeries.text = title
          AmiiboOptionsType.GAME_SERIES -> binding.chipGameSeries.text = title
          AmiiboOptionsType.AMIIBO_TYPE -> binding.chipType.text = title
          AmiiboOptionsType.CHARACTER -> binding.chipCharacter.text = title
        }
        viewModel.updateAmiiboOptions(type, title)
      }

      show(this@MainFragment.childFragmentManager, "tag")
    }
  }
}
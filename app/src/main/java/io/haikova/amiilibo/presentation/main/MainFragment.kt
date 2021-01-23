package io.haikova.amiilibo.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import io.haikova.amiilibo.databinding.FragmentMainBinding
import io.haikova.amiilibo.presentation.common.ListItem

class MainFragment : Fragment() {

  private var _binding: FragmentMainBinding? = null
  private val binding get() = _binding!!

  private val glide by lazy { Glide.with(this) }
  private val amiiboAdapter by lazy { ListDelegationAdapter(
    MainAdapterDelegates.amiiboDelegate(glide) {  }
  ) }
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
    binding.recyclerViewAmiibo.adapter = amiiboAdapter

    viewModel.amiiboData.observe(viewLifecycleOwner, { data ->
      amiiboAdapter.items = data
      amiiboAdapter.notifyDataSetChanged()
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
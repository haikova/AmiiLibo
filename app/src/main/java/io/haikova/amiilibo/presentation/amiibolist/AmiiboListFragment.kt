package io.haikova.amiilibo.presentation.amiibolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.FragemntAmiiboListBinding
import io.haikova.amiilibo.presentation.amiibo.AmiiboDetailsActivity
import io.haikova.amiilibo.presentation.home.adapter.MainAdapterDelegates

@AndroidEntryPoint
class AmiiboListFragment : Fragment() {

  private var _binding: FragemntAmiiboListBinding? = null
  private val binding get() = _binding!!

  private val viewModel: AmiiboListViewModel by viewModels()

  private val glide by lazy { Glide.with(this) }
  private val amiiboAdapter by lazy {
    ListDelegationAdapter(
      MainAdapterDelegates.amiiboDelegate(glide) {
        openDetailsScreen(it.id)
      },
      MainAdapterDelegates.amiiboLoadingDelegate()
    )
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragemntAmiiboListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.recyclerViewAmiibo.adapter = amiiboAdapter

    viewModel.amiiboData.observe(viewLifecycleOwner) { data ->
      if (data.isEmpty()) {
        viewModel.getEmpyAmiiboData().let {
          binding.emptyIcon.isVisible = true
          binding.emptyTitleTextView.isVisible = true
          binding.emptyTextView.isVisible = true
          binding.emptyTitleTextView.text = it.title
          binding.emptyTextView.text = it.text
          amiiboAdapter.items = data
          amiiboAdapter.notifyDataSetChanged()
        }
      } else {
        binding.emptyIcon.isVisible = false
        binding.emptyTitleTextView.isVisible = false
        binding.emptyTextView.isVisible = false
        amiiboAdapter.items = data
        amiiboAdapter.notifyDataSetChanged()
      }
    }
  }

  fun openDetailsScreen(itemId: String) {
    val bundle = bundleOf(AmiiboDetailsActivity.ITEM_ID to itemId)
    findNavController().navigate(R.id.action_collectionFragment_to_amiiboDetailsActivity, bundle)
  }

  enum class AmiiboListType {
    COLLECTION, FAVOURITE
  }

  companion object {
    val LIST_TYPE = "list_type"
  }
}
package io.haikova.amiilibo.presentation.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import io.haikova.amiilibo.databinding.FragmentCollectionBinding
import io.haikova.amiilibo.presentation.amiibolist.AmiiboListFragment

class CollectionFragment : Fragment() {

  private var _binding: FragmentCollectionBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentCollectionBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val amiiboCollectionAdapter = AmiiboCollectionAdapter(this)
    binding.viewPager.adapter = amiiboCollectionAdapter
    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
      tab.text = when (position) {
        0 -> "Collection"
        1 -> "Wish List"
        else -> ""
      }
    }.attach()
  }
}

class AmiiboCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment {
    val fragment = AmiiboListFragment()
    fragment.arguments = Bundle().apply {
      putString(
        AmiiboListFragment.LIST_TYPE, when (position) {
          0 -> AmiiboListFragment.AmiiboListType.COLLECTION.name
          1 -> AmiiboListFragment.AmiiboListType.FAVOURITE.name
          else -> ""
        }
      )
    }
    return fragment
  }
}

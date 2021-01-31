package io.haikova.amiilibo.presentation.options

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.FragmentOptionsBinding

@AndroidEntryPoint
class OptionsDialogFragment : DialogFragment() {
  lateinit var action : (AmiiboOptionsType, String) -> Unit
  private var _binding: FragmentOptionsBinding? = null
  private val binding get() = _binding!!

  private val optionsViewModel: OptionsViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentOptionsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    with(binding) {
      chipGroup.setOnCheckedChangeListener { group, checkedId ->
        Log.d("meow", "meow $checkedId")
        optionsViewModel.selectOption(chipGroup.findViewById<Chip>(checkedId).text.toString())
      }
    }

    optionsViewModel.amiiboOptionsList.observe(viewLifecycleOwner) {
      it.forEach { title ->
        binding.chipGroup.addView(createChip(title))
      }
    }

    optionsViewModel.amiiboResult.observe(viewLifecycleOwner) {
      action.invoke(it.first, it.second)
      dismiss()
    }
  }

  private fun createChip(title: String): Chip {
    return Chip(requireContext()).apply {
      text = title
      isClickable = true
      isCheckable = true
    }
  }

  companion object {
    const val OPTIONS_TYPE = "Amiibo options type"
  }
}
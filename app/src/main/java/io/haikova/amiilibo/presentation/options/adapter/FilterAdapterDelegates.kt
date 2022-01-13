package io.haikova.amiilibo.presentation.options.adapter


import android.util.Log
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.haikova.amiilibo.databinding.ItemOptionBinding
import io.haikova.amiilibo.databinding.ItemOptionsListBinding
import io.haikova.amiilibo.databinding.ItemTitleFilterBinding
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.home.adapter.AmiiboItem

object FilterAdapterDelegates {

  fun titleDelegate() = adapterDelegateViewBinding<TitleItem, ListItem, ItemTitleFilterBinding>(
    { layoutInflater, root -> ItemTitleFilterBinding.inflate(layoutInflater, root, false) }
  ) {
    bind {
      with(binding) {
        titleTextVIew.text = item.title
      }
    }
  }

  fun optionDelegate(
    itemClickedListener: (OptionItem) -> Unit
  ) = adapterDelegateViewBinding<OptionItem, ListItem, ItemOptionBinding>(
    { layoutInflater, root -> ItemOptionBinding.inflate(layoutInflater, root, false) }
  ) {
    binding.chip.setOnClickListener { v ->
      itemClickedListener(item.copy(isSelected = !item.isSelected))
    }
    bind {
      with(binding) {
        chip.text = item.name
        chip.isChecked = item.isSelected

        Log.d("meow ", "meow $item")
      }
    }
  }

  fun optionsListDelegate(
    itemClickedListener: (OptionItem) -> Unit
  ) =
    adapterDelegateViewBinding<OptionsDataItem, ListItem, ItemOptionsListBinding>(
      { layoutInflater, root -> ItemOptionsListBinding.inflate(layoutInflater, root, false) }
    ) {
      bind {
        with(binding) {
          val adapter = ListDelegationAdapter(optionDelegate(itemClickedListener))
          optionsRecyclerView.adapter = adapter
          adapter.items = item.optionslist
        }
      }
    }
}
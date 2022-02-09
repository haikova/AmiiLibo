package io.haikova.amiilibo.presentation.options.adapter


import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.haikova.amiilibo.databinding.ItemOptionBinding
import io.haikova.amiilibo.databinding.ItemOptionsListBinding
import io.haikova.amiilibo.databinding.ItemTitleFilterBinding
import io.haikova.amiilibo.presentation.common.ListItem

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
      }
    }
  }

  fun optionsListDelegate(
    itemClickedListener: (OptionItem) -> Unit
  ) =
    adapterDelegateViewBinding<OptionsDataItem, ListItem, ItemOptionsListBinding>(
      { layoutInflater, root -> ItemOptionsListBinding.inflate(layoutInflater, root, false) }
    ) {
      val adapter = AsyncListDifferDelegationAdapter(
        differCallbackOptionsList,
        AdapterDelegatesManager<List<ListItem>>()
          .addDelegate(optionDelegate(itemClickedListener))
      )
      binding.optionsRecyclerView.adapter = adapter
      bind {
        with(binding) {
          adapter.items = item.optionslist
        }
      }
    }

  private val differCallbackOptionsList: DiffUtil.ItemCallback<ListItem> = object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
      return if (oldItem is OptionItem && newItem is OptionItem) {
          oldItem.name == newItem.name && oldItem.type == newItem.type
      } else false
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
      return if (oldItem is OptionItem && newItem is OptionItem) {
        oldItem == newItem
      } else false
    }
  }

  val differCallback: DiffUtil.ItemCallback<ListItem> = object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
      return if (oldItem is OptionsDataItem && newItem is OptionsDataItem) {
        oldItem.type == newItem.type
      } else oldItem is TitleItem && newItem is TitleItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
      return if (oldItem is OptionsDataItem && newItem is OptionsDataItem) {
        oldItem.optionslist == newItem.optionslist
      }
      else if (oldItem is TitleItem && newItem is TitleItem) {
        oldItem == newItem
      } else false
    }
  }
}
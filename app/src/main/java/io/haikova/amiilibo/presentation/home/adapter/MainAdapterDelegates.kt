package io.haikova.amiilibo.presentation.home.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.haikova.amiilibo.R
import io.haikova.amiilibo.databinding.ItemHomeAmiiboBinding
import io.haikova.amiilibo.databinding.ItemHomeLoadingAmiiboBinding
import io.haikova.amiilibo.presentation.common.ListItem

object MainAdapterDelegates {

  fun amiiboDelegate(
    glide: RequestManager,
    itemClickedListener: (AmiiboItem) -> Unit
  ) = adapterDelegateViewBinding<AmiiboItem, ListItem, ItemHomeAmiiboBinding>(
    { layoutInflater, root -> ItemHomeAmiiboBinding.inflate(layoutInflater, root, false) }
  ) {

    binding.root.setOnClickListener {
      itemClickedListener(item)
    }
    bind {
      with(binding) {
        glide
          .load(item.image)
          .into(imageViewAmiibo)
        nameText.text = item.name
        gameText.text = item.game
        when {
          item.isOwned -> {
            itemIcon.setImageDrawable(getDrawable(R.drawable.ic_collection))
            itemIcon.isVisible = true
          }
          item.isFavourite -> {
            itemIcon.setImageDrawable(getDrawable(R.drawable.ic_favourite_added))
            itemIcon.isVisible = true
          }
          else -> {
            itemIcon.isVisible = false
          }
        }
      }
    }
    onViewRecycled {
      glide.clear(binding.imageViewAmiibo)
    }
  }

  fun amiiboLoadingDelegate() =
    adapterDelegateViewBinding<AmiiboLoadingItem, ListItem, ItemHomeLoadingAmiiboBinding>(
      { layoutInflater, root -> ItemHomeLoadingAmiiboBinding.inflate(layoutInflater, root, false) }
    ) {}

  val differCallback: DiffUtil.ItemCallback<ListItem> = object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
      return if (oldItem is AmiiboItem && newItem is AmiiboItem) {
        oldItem.id == newItem.id
      } else false
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
      return if (oldItem is AmiiboItem && newItem is AmiiboItem) {
        oldItem == newItem
      } else false
    }
  }

}
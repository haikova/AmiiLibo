package io.haikova.amiilibo.presentation.main.adapter

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.haikova.amiilibo.databinding.ItemHomeAmiiboBinding
import io.haikova.amiilibo.presentation.common.ListItem

object MainAdapterDelegates {

  fun amiiboDelegate(
    glide: RequestManager,
    itemClickedListener: (AmiiboItem) -> Unit
  ) = adapterDelegateViewBinding<AmiiboItem, ListItem, ItemHomeAmiiboBinding>(
    { layoutInflater, root -> ItemHomeAmiiboBinding.inflate(layoutInflater, root, false) }
  ) {

    binding.imageViewAmiibo.setOnClickListener {
      itemClickedListener(item)
    }
    bind {
      glide
        .load(item.image)
        .into(binding.imageViewAmiibo)
    }
    onViewRecycled {
      glide.clear(binding.imageViewAmiibo)
    }
  }
}
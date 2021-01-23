package io.haikova.amiilibo.presentation.main

import android.util.Log
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

    Log.d("meow", "qqq")
/*    binding.imageViewAmiibo.setOnClickListener {
      itemClickedListener(item)
    }*/
    bind {
      Log.d("meow", "www")
      glide
        .load(item.image)
        .into(binding.imageViewAmiibo)
    }
    onViewRecycled {
      glide.clear(binding.imageViewAmiibo)
    }
  }
}
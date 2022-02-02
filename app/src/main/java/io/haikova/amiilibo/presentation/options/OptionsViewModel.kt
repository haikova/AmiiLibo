package io.haikova.amiilibo.presentation.options

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.haikova.amiilibo.data.OptionModel
import io.haikova.amiilibo.data.options.OptionsRepository
import io.haikova.amiilibo.presentation.common.ListItem
import io.haikova.amiilibo.presentation.options.adapter.OptionItem
import io.haikova.amiilibo.presentation.options.adapter.OptionsDataItem
import io.haikova.amiilibo.presentation.options.adapter.OptionsType
import io.haikova.amiilibo.presentation.options.adapter.TitleItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(
  private val optionsRepository: OptionsRepository
) : ViewModel() {

  private val _data: MutableLiveData<List<ListItem>> = MutableLiveData()
  val data: LiveData<List<ListItem>> = _data
  private val _selected: MutableLiveData<Set<OptionModel>> = MutableLiveData(hashSetOf())
  val selected: LiveData<Set<OptionModel>> = _selected
  lateinit var options: List<OptionModel>

  init {
    viewModelScope.launch(Dispatchers.IO) {
      options = optionsRepository.getOptions()
      val list = mutableListOf<ListItem>()
      list.apply {
        add(TitleItem("Series"))
        add(OptionsDataItem(options.filter { it.type.toString() == OptionsType.AMIIBO_SERIES.toString() }.map { it.item() }, OptionsType.AMIIBO_SERIES))
        add(TitleItem("Game series"))
        add(OptionsDataItem(options.filter { it.type.toString() == OptionsType.GAME_SERIES.toString() }.map { it.item() }, OptionsType.GAME_SERIES))
        add(TitleItem("Character"))
        add(OptionsDataItem(options.filter { it.type.toString() == OptionsType.CHARACTER.toString() }.map { it.item() }, OptionsType.CHARACTER))
      }
      _data.postValue(list)
    }
  }

  fun optionClicked(item: OptionItem) {
    selected.value?.let {
      val temp: MutableSet<OptionModel> = it.toMutableSet()
      temp.addAll(it)
      if (item.isSelected) {
        temp.add(item.model())
      } else {
        temp.remove(item.model())
      }
      _selected.value = temp
    }
    updateData(item)
  }

  fun updateData(item: OptionItem) {
    data.value?.let {
      val data = it.toMutableList()
      val optionsList = data.filterIsInstance<OptionsDataItem>().first {it.type == item.type}.optionslist.toMutableList()
      optionsList.forEachIndexed { index, optionItem ->
        if (optionItem.name == item.name) {
          optionsList[index] = item
          return@forEachIndexed
        }
      }
      data[data.indexOf(data.filterIsInstance<OptionsDataItem>().first {it.type == item.type})] = OptionsDataItem(optionsList, item.type)
      _data.postValue(data)
    }
  }

  fun removeOption(item: OptionModel) {
    selected.value?.let {
      val temp: MutableSet<OptionModel> = it.toMutableSet()
      temp.addAll(it)
      temp.remove(item)
      _selected.value = temp
    }
    updateData(item.item())
  }
}

private fun OptionItem.model(): OptionModel {
  return OptionModel(
    name = this.name,
    type = io.haikova.amiilibo.data.AmiiboOptionsType.valueOf(this.type.toString())
  )

}

private fun OptionModel.item(isSelected: Boolean = false): OptionItem {
  return OptionItem(
    name = this.name,
    isSelected = isSelected,
    type = OptionsType.valueOf(this.type.toString())
  )
}

enum class AmiiboOptionsType {
  AMIIBO_SERIES, GAME_SERIES, AMIIBO_TYPE, CHARACTER
}

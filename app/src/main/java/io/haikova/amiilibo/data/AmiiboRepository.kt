package io.haikova.amiilibo.data

import io.haikova.amiilibo.presentation.main.AmiiboOptionsData

interface AmiiboRepository {
  suspend fun getAllAmiibo(): List<AmiiboModel>
  suspend fun getAmiiboByOptions(amiiboOptionsData: AmiiboOptionsData): List<AmiiboModel>
  suspend fun getAmiiboDetails(id: String): AmiiboModel
}
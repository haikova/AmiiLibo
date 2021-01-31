package io.haikova.amiilibo.data

interface AmiiboDataSource {
  suspend fun getAllAmiibo(): List<AmiiboModel>
}
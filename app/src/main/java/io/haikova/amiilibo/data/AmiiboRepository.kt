package io.haikova.amiilibo.data

interface AmiiboRepository {
  abstract suspend fun getAllAmiibo(): List<AmiiboModel>
}
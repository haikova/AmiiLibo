package io.haikova.amiilibo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AmiiboDao {

  @Query("SELECT * FROM amiibos")
  fun getAllAmiiboLiveData(): LiveData<List<AmiiboEntity>>

  @Query("SELECT * FROM amiibos")
  fun getAllAmiibo(): List<AmiiboEntity>

  @Query(
    """SELECT * FROM amiibos 
        WHERE (:amiiboSeries IS NULL OR amiiboSeries IN (:amiiboSeries)) 
        AND (:gameSeries IS NULL OR gameSeries LIKE :gameSeries) 
        AND (:amiiboType IS NULL OR type LIKE :amiiboType) 
        AND (:character IS NULL OR character LIKE :character)"""
  )
  fun getAmiiboByOptions(
    amiiboSeries: List<String>? = null,
    gameSeries: String? = null,
    amiiboType: String? = null,
    character: String? = null
  ): List<AmiiboEntity>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertAllAmiibo(amiiboList: List<AmiiboEntity>)

  @Update(entity = AmiiboEntity::class)
  fun updateFavouriteStateAmiibo(update: FavouriteUpdate)

  @Update(entity = AmiiboEntity::class)
  fun updateOwnedStateAmiibo(update: OwnedUpdate)

  @Query(
    """SELECT * FROM amiibos
    WHERE id = :idItem"""
  )
  fun getAmiiboDetails(idItem: String): LiveData<AmiiboEntity>

  @Query("DELETE FROM amiibos")
  fun clearAmiibos()

  @Query(
    """SELECT * FROM options
    WHERE type = :type"""
  )
  fun getOptionsByType(type: String): List<OptionEntity>

  @Query("SELECT * FROM options")
  fun getAllOptions(): List<OptionEntity>


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertOptions(list: List<OptionEntity>)

  @Query("DELETE FROM options")
  fun clearOptions()

  @Query(
    """SELECT * FROM amiibos
      WHERE isOwned = 1"""
  )
  fun getAmiiboCollectionList(): LiveData<List<AmiiboEntity>>

  @Query(
    """SELECT * FROM amiibos
      WHERE isFavourite = 1"""
  )
  fun getAmiiboFavouriteList(): LiveData<List<AmiiboEntity>>
}

package io.haikova.amiilibo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AmiiboDao {

  @Query("SELECT * FROM amiibos")
  fun getAllAmiibo(): List<AmiiboEntity>

  @Query(
    """SELECT * FROM amiibos 
        WHERE (:amiiboSeries IS NULL OR amiiboSeries LIKE :amiiboSeries) 
        AND (:gameSeries IS NULL OR gameSeries LIKE :gameSeries) 
        AND (:amiiboType IS NULL OR type LIKE :amiiboType) 
        AND (:character IS NULL OR character LIKE :character)"""
  )
  fun getAmiiboByOptions(
    amiiboSeries: String? = null,
    gameSeries: String? = null,
    amiiboType: String? = null,
    character: String? = null
  ): List<AmiiboEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAllAmiibo(amiiboList: List<AmiiboEntity>)

  @Query(
    """SELECT * FROM amiibos
    WHERE id = :idItem"""
  )
  fun getAmiiboDetails(idItem: String): AmiiboEntity

  @Query("DELETE FROM amiibos")
  fun clearAmiibos()

  @Query(
    """SELECT * FROM options
    WHERE type = :type"""
  )
  fun getOptionsByType(type: String): List<OptionEntity>


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertOptions(list: List<OptionEntity>)

  @Query("DELETE FROM options")
  fun clearOptions()
}

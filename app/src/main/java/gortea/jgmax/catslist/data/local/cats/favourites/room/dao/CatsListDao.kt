package gortea.jgmax.catslist.data.local.cats.favourites.room.dao

import androidx.room.*
import gortea.jgmax.catslist.data.local.cats.favourites.room.entity.CatsListEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CatsListDao {
    @Query("SELECT * FROM ${CatsListEntity.TABLE_NAME}")
    fun getAll() : Single<List<CatsListEntity>>

    @Insert(entity = CatsListEntity::class, onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun addEntity(entity: CatsListEntity) : Completable

    @Delete(entity = CatsListEntity::class)
    fun removeEntity(entity: CatsListEntity) : Completable
}
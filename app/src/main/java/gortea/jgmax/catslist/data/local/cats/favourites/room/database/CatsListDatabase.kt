package gortea.jgmax.catslist.data.local.cats.favourites.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import gortea.jgmax.catslist.data.local.cats.favourites.room.dao.CatsListDao
import gortea.jgmax.catslist.data.local.cats.favourites.room.entity.CatsListEntity

@Database(entities = [CatsListEntity::class], version = 1)
abstract class CatsListDatabase : RoomDatabase() {
    abstract fun CatsListDao(): CatsListDao

    companion object {
        const val DATABASE_NAME = "cats_database"
    }
}
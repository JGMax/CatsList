package gortea.jgmax.catslist.data.local.cats.favourites.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import gortea.jgmax.catslist.data.local.cats.favourites.room.entity.CatsListEntity.Companion.TABLE_NAME
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem

@Entity(tableName = TABLE_NAME)
data class CatsListEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "image_url") val url: String
) {
    companion object {
        const val TABLE_NAME = "favourite_cats"
    }
}

fun CatsListEntity.toItem(): CatsListItem {
    return CatsListItem(
        id = this.id,
        url = this.url
    )
}
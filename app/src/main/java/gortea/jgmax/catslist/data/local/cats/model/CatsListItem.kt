package gortea.jgmax.catslist.data.local.cats.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import gortea.jgmax.catslist.data.local.cats.favourites.room.entity.CatsListEntity

data class CatsListItem(
    val id: String,
    val url: String
) : Parcelable {
    constructor(inParcel: Parcel) : this(
        inParcel.createStringArray()!![0],
        inParcel.createStringArray()!![1]
    )
    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<CatsListItem> {
            override fun createFromParcel(source: Parcel?): CatsListItem {
                return CatsListItem(requireNotNull(source))
            }

            override fun newArray(size: Int): Array<CatsListItem> {
                return arrayOf()
            }
        }
    }
}

fun CatsListItem.toEntity(): CatsListEntity {
    return CatsListEntity(
        id = this.id,
        url = this.url
    )
}
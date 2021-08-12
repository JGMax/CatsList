package gortea.jgmax.catslist.ui.presenters

import android.app.DownloadManager
import android.content.BroadcastReceiver
import gortea.jgmax.catslist.data.local.cats.favourites.room.dao.CatsListDao

interface CatsDetailPresenter {
    fun removeFromFavourites(dao: CatsListDao)
    fun addToFavourites(dao: CatsListDao)
    fun download()
    fun attachDownloadService(service: DownloadManager)
    fun getDownloadReceiver(): BroadcastReceiver
}
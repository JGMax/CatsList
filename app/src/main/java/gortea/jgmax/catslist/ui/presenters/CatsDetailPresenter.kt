package gortea.jgmax.catslist.ui.presenters

import android.app.DownloadManager
import android.content.BroadcastReceiver

interface CatsDetailPresenter {
    fun addToFavourites()
    fun download()
    fun attachDownloadService(service: DownloadManager)
    fun getDownloadReceiver(): BroadcastReceiver
}
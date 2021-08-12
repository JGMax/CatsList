package gortea.jgmax.catslist.ui.presenters.implementations

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.data.local.cats.favourites.room.dao.CatsListDao
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.data.local.cats.model.toEntity
import gortea.jgmax.catslist.ui.presenters.CatsDetailPresenter
import gortea.jgmax.catslist.ui.view.CatsDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CatsDetailPresenter(val item: CatsListItem?) : MvpPresenter<CatsDetailView>(), CatsDetailPresenter {
    private var downloadManager: DownloadManager? = null
    private var downloadId = 0L
    private var disposeBag = CompositeDisposable()

    private val downloadBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadId == id) {
                viewState.onSuccessDownload()
            }
        }
    }

    override fun addToFavourites(dao: CatsListDao) {
        if(item == null) return
        viewState.onStartFavourites()
        val disposable = dao.addEntity(item.toEntity())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                viewState.onSuccessFavourites()
            }, {
                viewState.showError(R.string.unknown_error)
            })
        disposeBag.add(disposable)
    }

    override fun removeFromFavourites(dao: CatsListDao) {
        if(item == null) return
        viewState.onStartFavourites()
        val disposable = dao.removeEntity(item.toEntity())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                viewState.onSuccessFavourites()
            }, {
                viewState.showError(R.string.unknown_error)
            })
        disposeBag.add(disposable)
    }

    override fun download() {
        if (item == null || downloadManager == null) {
            viewState.showError(R.string.unknown_error)
            return
        }
        viewState.onStartDownload()
        val uri = Uri.parse(item.url)
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(uri.lastPathSegment)
            .setDescription("Downloading...")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.lastPathSegment)
        downloadId = downloadManager?.enqueue(request) ?: return
    }

    override fun attachDownloadService(service: DownloadManager) {
        downloadManager = service
    }

    override fun getDownloadReceiver(): BroadcastReceiver = downloadBroadcastReceiver

    override fun onDestroy() {
        disposeBag.dispose()
    }
}
package gortea.jgmax.catslist.ui.presenters.implementations

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.ui.presenters.CatsDetailPresenter
import gortea.jgmax.catslist.ui.view.CatsDetailView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CatsDetailPresenter(val item: CatsListItem?) : MvpPresenter<CatsDetailView>(), CatsDetailPresenter {

    override fun addToFavourites() {

    }

    override fun download() {
        if (item == null) return
        val uri = Uri.parse(item.url)
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(uri.lastPathSegment)
            .setDescription("Downloading...")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.lastPathSegment)
        viewState.download(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("presenter", "destroyed")
    }
}
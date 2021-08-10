package gortea.jgmax.catslist.ui.presenters.implementations

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.ui.presenters.CatsDetailPresenter
import gortea.jgmax.catslist.ui.view.CatsDetailView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CatsDetailPresenter : MvpPresenter<CatsDetailView>(), CatsDetailPresenter {
    override fun addToFavourites(item: CatsListItem) {

    }

    override fun download(url: String) {
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle("Cat image")
            .setDescription("Downloading...")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
            "cat.jpg")
        viewState.download(request)
    }
}
package gortea.jgmax.cats.fullview.fragment

import android.app.DownloadManager
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gortea.jgmax.cats.R
import gortea.jgmax.cats.app.state.LoadingState
import gortea.jgmax.cats.app.state.UrlCheckState
import gortea.jgmax.cats.navigation.coordinator.Coordinator
import javax.inject.Inject

class FullViewViewModel @Inject constructor(
    private val coordinator: Coordinator
) : ViewModel() {
    private val loadingState = MutableLiveData<LoadingState>(LoadingState.Default)
    private val checkState = MutableLiveData<UrlCheckState>(UrlCheckState.Default)
    private var url: String? = null
    private var downloadId = -1L

    fun getLoadingState(): LiveData<LoadingState> = loadingState
    fun getCheckState(): LiveData<UrlCheckState> = checkState

    private fun checkUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            checkState.value = UrlCheckState.Success(url)
            this.url = url
        } else {
            checkState.value = UrlCheckState.Fail(R.string.unknown_error)
            coordinator.popBackStack()
        }
    }

    fun onReceiveBroadcast(service: DownloadManager, intent: Intent) {
        val action = intent.action
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
            val query = DownloadManager.Query()
            query.setFilterById(id)
            val c: Cursor = service.query(query)
            if (c.moveToFirst()) {
                val columnIndex: Int = c.getColumnIndex(DownloadManager.COLUMN_STATUS)
                when (c.getInt(columnIndex)) {
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        loadingState.value = LoadingState.Success
                    }
                    DownloadManager.STATUS_FAILED -> {
                        loadingState.value = LoadingState.Failed(R.string.connection_error)
                    }
                }
            }
        }
    }

    fun download(downloadManager: DownloadManager, downloadDescription: String) {
        url?.let {
            loadingState.value = LoadingState.Loading
            val uri = Uri.parse(it)
            val request = DownloadManager.Request(uri)
            request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        or DownloadManager.Request.NETWORK_MOBILE
            )
                .setAllowedOverRoaming(true)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(uri.lastPathSegment)
                .setDescription(downloadDescription)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    uri.lastPathSegment
                )
            downloadId = downloadManager.enqueue(request)
        } ?: run {
            loadingState.value = LoadingState.Failed(R.string.unknown_error)
        }

        Log.e("download", "init $url")
    }

    fun initArgs(args: Bundle?) {
        checkUrl(args?.getString(IMAGE_URL_ARG))
    }

    companion object {
        const val IMAGE_URL_ARG = "image_arg"
    }
}
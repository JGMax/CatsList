package gortea.jgmax.cats.fullview.fragment

import android.app.DownloadManager
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gortea.jgmax.cats.R
import gortea.jgmax.cats.app.state.LoadingState
import gortea.jgmax.cats.navigation.coordinator.Coordinator
import javax.inject.Inject

class FullViewViewModel @Inject constructor(
    private val coordinator: Coordinator
) : ViewModel() {
    private val loadingState = MutableLiveData<LoadingState>(LoadingState.Default)
    private var downloadId = -1L

    fun getLoadingState(): LiveData<LoadingState> = loadingState

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

    fun checkUrl(url: String) {
        if (url.isEmpty()) {
            coordinator.popBackStack()
        }
    }

    fun download(url: String, downloadManager: DownloadManager, downloadDescription: String) {
        loadingState.value = LoadingState.Loading
        val uri = Uri.parse(url)
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
    }
}

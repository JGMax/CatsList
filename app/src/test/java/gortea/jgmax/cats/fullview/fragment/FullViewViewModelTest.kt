package gortea.jgmax.cats.fullview.fragment

import android.app.DownloadManager
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gortea.jgmax.cats.app.state.LoadingState
import gortea.jgmax.cats.catslist.observeOnce
import gortea.jgmax.cats.navigation.coordinator.Coordinator
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class FullViewViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun download_wakes_loading_state() {
        val coordinator = mockk<Coordinator>()
        val downloadManager = spyk<DownloadManager>()

        val viewModel = FullViewViewModel(coordinator)
        try {
            viewModel.download("", downloadManager, "")
        } catch (e: RuntimeException) {}
        viewModel.getLoadingState().observeOnce {
            assertEquals(true, it is LoadingState.Loading)
        }
    }
}
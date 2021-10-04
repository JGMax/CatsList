package gortea.jgmax.cats.catslist.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gortea.jgmax.cats.app.state.LoadingState
import gortea.jgmax.cats.catslist.RxImmediateSchedulerRule
import gortea.jgmax.cats.catslist.data.remote.CatsApi
import gortea.jgmax.cats.catslist.observeOnce
import gortea.jgmax.cats.catslist.repository.CatsListRepository
import gortea.jgmax.cats.navigation.coordinator.Coordinator
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test

class CatsListViewModelTest {
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun fetchCatsList_wakes_state_success() {
        val coordinator = mockk<Coordinator>()
        val api = spyk<CatsApi>()
        val repository = spyk(CatsListRepository(api))

        every { api.getCatsList(any()) } answers { Single.just(listOf()) }

        val viewModel = CatsListViewModel(coordinator, repository)
        viewModel.getStateLiveData().observeOnce {
            assertEquals(true, it is LoadingState.Default)
        }
        viewModel.fetchCatsList()
        viewModel.getStateLiveData().observeOnce {
            assertEquals(true, it is LoadingState.Success)
        }
    }
}
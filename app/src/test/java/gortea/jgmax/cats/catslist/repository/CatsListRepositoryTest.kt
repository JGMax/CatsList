package gortea.jgmax.cats.catslist.repository

import gortea.jgmax.cats.catslist.RxImmediateSchedulerRule
import gortea.jgmax.cats.catslist.data.model.CatModel
import gortea.jgmax.cats.catslist.data.remote.CatsApi
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Single
import org.junit.ClassRule
import org.junit.Test

class CatsListRepositoryTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Test
    fun getList_wakes_api_method() {
        val api = spyk<CatsApi>()
        every { api.getCatsList(any()) } answers { Single.just(listOf()) }
        CatsListRepository(api).getList(20)
        verify { api.getCatsList(any()) }
    }
}